/* *****************************************************************************
 *  Name: Zihang Wang
 *  Date: 07/07/2019
 *  Description: SeamCarver class to remove the lowest energy pixels
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;

    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("The input is null");
        }

        this.width = picture.width();
        this.height = picture.height();

        Picture newPic = new Picture(width, height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                newPic.setRGB(i, j, picture.getRGB(i, j));
            }
        }

        this.picture = newPic;

    }

    public Picture picture() {
        Picture newPic = new Picture(width, height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                newPic.setRGB(i, j, picture.getRGB(i, j));
            }
        }
        return newPic;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public double energy(int x, int y) {
        if (!validateX(x) || !validateY(y)) {
            throw new IllegalArgumentException("The input is out of range");
        }

        if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
            return 1000.0;
        }

        int rgbR = picture.getRGB(x + 1, y);
        int rgbL = picture.getRGB(x - 1, y);
        int rgbU = picture.getRGB(x, y + 1);
        int rgbD = picture.getRGB(x, y - 1);
        int diffHR = ((rgbR >> 16) & 0xFF) - ((rgbL >> 16) & 0xFF);
        int diffHG = ((rgbR >> 8) & 0xFF) - ((rgbL >> 8) & 0xFF);
        int diffHB = ((rgbR >> 0) & 0xFF) - ((rgbL >> 0) & 0xFF);
        int diffVR = ((rgbU >> 16) & 0xFF) - ((rgbD >> 16) & 0xFF);
        int diffVG = ((rgbU >> 8) & 0xFF) - ((rgbD >> 8) & 0xFF);
        int diffVB = ((rgbU >> 0) & 0xFF) - ((rgbD >> 0) & 0xFF);
        return Math.sqrt(diffHR * diffHR + diffHG * diffHG + diffHB * diffHB + diffVR * diffVR
                                 + diffVG * diffVG + diffVB * diffVB);
    }

    public int[] findHorizontalSeam() {
        int[] seam = new int[width];

        if (height == 1 || width == 1) {
            for (int i = 0; i < width; i++) {
                seam[i] = 0;
            }
            return seam;
        }


        double[][] energies = computeEnergies();
        double[][] minEnergies = new double[width][height];
        int[][] index = new int[width][height];

        for (int j = 0; j < height; j++) {
            minEnergies[0][j] = 1000.0;
        }

        for (int i = 1; i < width; i++) {
            for (int j = 0; j < height; j++) {
                minEnergies[i][j] = Double.POSITIVE_INFINITY;
            }
        }

        for (int i = 1; i < width; i++) {
            for (int j = 0; j < height; j++) {

                if (j == 0) {
                    for (int k = j; k <= j + 1; k++) {
                        if (energies[i][j] + minEnergies[i - 1][k] < minEnergies[i][j]) {
                            minEnergies[i][j] = energies[i][j] + minEnergies[i - 1][k];
                            index[i][j] = k;
                        }
                    }
                }
                else if (j == height - 1) {
                    for (int k = j - 1; k <= j; k++) {
                        if (energies[i][j] + minEnergies[i - 1][k] < minEnergies[i][j]) {
                            minEnergies[i][j] = energies[i][j] + minEnergies[i - 1][k];
                            index[i][j] = k;
                        }
                    }
                }
                else {
                    for (int k = j - 1; k <= j + 1; k++) {
                        if (energies[i][j] + minEnergies[i - 1][k] < minEnergies[i][j]) {
                            minEnergies[i][j] = energies[i][j] + minEnergies[i - 1][k];
                            index[i][j] = k;
                        }
                    }
                }

            }
        }

        double min = minEnergies[width - 1][0];
        seam[width - 1] = 0;
        for (int j = 0; j < height; j++) {
            if (minEnergies[width - 1][j] < min) {
                min = minEnergies[width - 1][j];
                seam[width - 1] = j;
            }
        }

        for (int i = width - 1; i >= 1; i--) {
            seam[i - 1] = index[i][seam[i]];
        }

        return seam;
    }

    public int[] findVerticalSeam() {
        int[] seam = new int[height];

        if (width == 1 || height == 1) {
            for (int j = 0; j < height; j++) {
                seam[j] = 0;
            }
            return seam;
        }

        double[][] energies = computeEnergies();
        double[][] minEnergies = new double[width][height];
        int[][] index = new int[width][height];

        for (int i = 0; i < width; i++) {
            minEnergies[i][0] = 1000.0;
        }

        for (int i = 0; i < width; i++) {
            for (int j = 1; j < height; j++) {
                minEnergies[i][j] = Double.POSITIVE_INFINITY;
            }
        }

        for (int j = 1; j < height; j++) {
            for (int i = 0; i < width; i++) {

                if (i == 0) {
                    for (int k = i; k <= i + 1; k++) {
                        if (energies[i][j] + minEnergies[k][j - 1] < minEnergies[i][j]) {
                            minEnergies[i][j] = energies[i][j] + minEnergies[k][j - 1];
                            index[i][j] = k;
                        }
                    }
                }
                else if (i == width - 1) {
                    for (int k = i - 1; k <= i; k++) {
                        if (energies[i][j] + minEnergies[k][j - 1] < minEnergies[i][j]) {
                            minEnergies[i][j] = energies[i][j] + minEnergies[k][j - 1];
                            index[i][j] = k;
                        }
                    }
                }
                else {
                    for (int k = i - 1; k <= i + 1; k++) {
                        if (energies[i][j] + minEnergies[k][j - 1] < minEnergies[i][j]) {
                            minEnergies[i][j] = energies[i][j] + minEnergies[k][j - 1];
                            index[i][j] = k;
                        }
                    }
                }

            }
        }

        double min = minEnergies[0][height - 1];
        seam[height - 1] = 0;
        for (int i = 0; i < width; i++) {
            if (minEnergies[i][height - 1] < min) {
                min = minEnergies[i][height - 1];
                seam[height - 1] = i;
            }
        }

        for (int j = height - 1; j >= 1; j--) {
            seam[j - 1] = index[seam[j]][j];
        }

        return seam;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (height <= 1) {
            throw new IllegalArgumentException("This picture can't be removed any pixel");
        }

        if (seam == null) {
            throw new IllegalArgumentException("The input is null");
        }

        if (seam.length != width) {
            throw new IllegalArgumentException("The input is not the same lenght of the width");
        }

        for (int i = 0; i < width; i++) {
            if (!validateY(seam[i])) {
                throw new IllegalArgumentException("This is illegal input");
            }
        }

        for (int i = 0; i < width - 1; i++) {
            int diff = seam[i + 1] - seam[i];
            if (Math.abs(diff) > 1) {
                throw new IllegalArgumentException("The input is wrong");
            }
        }

        Picture newPic = new Picture(width, height - 1);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height - 1; j++) {
                if (j < seam[i]) {
                    newPic.setRGB(i, j, this.picture.getRGB(i, j));
                }
                else {
                    newPic.setRGB(i, j, this.picture.getRGB(i, j + 1));
                }
            }
        }

        this.picture = newPic;
        this.height = height - 1;
    }

    public void removeVerticalSeam(int[] seam) {
        if (width <= 1) {
            throw new IllegalArgumentException("This picture can't be removed any pixel");
        }

        if (seam == null) {
            throw new IllegalArgumentException("The input is null");
        }

        if (seam.length != picture.height()) {
            throw new IllegalArgumentException("The input is not the same lenght of the width");
        }

        for (int i = 0; i < height; i++) {
            if (!validateX(seam[i])) {
                throw new IllegalArgumentException("This is illegal input");
            }
        }

        for (int i = 0; i < height - 1; i++) {
            int diff = seam[i + 1] - seam[i];
            if (Math.abs(diff) > 1) {
                throw new IllegalArgumentException("The input is wrong");
            }
        }

        Picture newPic = new Picture(width - 1, height);
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width - 1; i++) {
                if (i < seam[j]) {
                    newPic.setRGB(i, j, this.picture.getRGB(i, j));
                }
                else {
                    newPic.setRGB(i, j, this.picture.getRGB(i + 1, j));
                }
            }
        }

        this.picture = newPic;
        this.width = width - 1;
    }

    private double[][] computeEnergies() {
        double[][] energies = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                energies[i][j] = energy(i, j);
            }
        }
        return energies;
    }

    private boolean validateX(int x) {
        if (x >= 0 && x < picture.width()) {
            return true;
        }
        return false;
    }

    private boolean validateY(int y) {
        if (y >= 0 && y < picture.height()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {

    }
}
