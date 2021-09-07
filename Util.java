package final_project;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Util {

	static boolean isNumber(String str) { // 檢查是否為數字
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	static int getR(int color) {
		return (color & 0x00ff0000) >> 16;
	}

	static int getG(int color) {
		return (color & 0x0000ff00) >> 8;
	}

	static int getB(int color) {
		return color & 0x000000ff;
	}

	static int getColor(int r, int g, int b) {
		r = checkColor(r);
		g = checkColor(g);
		b = checkColor(b);
		return (255 << 24) | (r << 16) | (g << 8) | b;
	}

	static int checkColor(int value) {
		if (value > 255) {
			return 255;
		} else if (value < 0) {
			return 0;
		} else {
			return value;
		}
	}

	static int checkBound(int value, int length) {
		if (value > length - 1) {
			return length - 1;
		} else if (value < 0) {
			return 0;
		} else {
			return value;
		}
	}

	static boolean isOutOfBound(double value, int length) {
		if (value > length - 1) {
			return true;
		} else if (value < 0) {
			return true;
		} else {
			return false;
		}
	}

	static BufferedImage inverse(int[][][] data) {
		int newHeight = data.length;
		int newWidth = data[0].length;
		BufferedImage newImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < newHeight; i++) {
			for (int j = 0; j < newWidth; j++) {
				int r = 255 - data[i][j][0];
				int g = 255 - data[i][j][1];
				int b = 255 - data[i][j][2];
				int color = getColor(r, g, b);
				newImg.setRGB(j, i, color);
			}
		}
		return newImg;
	}

	static int[][] gray(int[][][] data) {
		int[][] result = new int[data.length][data[0].length];
		int height = data.length;
		int width = data[0].length;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				float number = (data[i][j][0] * 0.299f) + (data[i][j][1] * 0.587f) + (data[i][j][2] * 0.114f);
				result[i][j] = checkColor(Math.round(number));
			}
		}
		return result;
	}

	static BufferedImage upDown(int[][][] data) {
		int newHeight = data.length;
		int newWidth = data[0].length;
		BufferedImage newImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < newHeight; i++) {
			for (int j = 0; j < newWidth; j++) {
				int r = data[newHeight - 1 - i][j][0];
				int g = data[newHeight - 1 - i][j][1];
				int b = data[newHeight - 1 - i][j][2];
				int color = getColor(r, g, b);
				newImg.setRGB(j, i, color);
			}
		}
		return newImg;
	}

	static BufferedImage leftRight(int[][][] data) {
		int newHeight = data.length;
		int newWidth = data[0].length;
		BufferedImage newImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < newHeight; i++) {
			for (int j = 0; j < newWidth; j++) {
				int r = data[i][newWidth - 1 - j][0];
				int g = data[i][newWidth - 1 - j][1];
				int b = data[i][newWidth - 1 - j][2];
				int color = getColor(r, g, b);
				newImg.setRGB(j, i, color);
			}
		}
		return newImg;
	}

	static BufferedImage rotateRight(int[][][] data) {
		int newHeight = data[0].length;
		int newWidth = data.length;
		BufferedImage newImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < newHeight; i++) {
			for (int j = 0; j < newWidth; j++) {
				int r = data[newWidth - 1 - j][i][0];
				int g = data[newWidth - 1 - j][i][1];
				int b = data[newWidth - 1 - j][i][2];
				int color = getColor(r, g, b);
				newImg.setRGB(j, i, color);
			}
		}
		return newImg;
	}

	static BufferedImage rotateLeft(int[][][] data) {
		int newHeight = data[0].length;
		int newWidth = data.length;
		BufferedImage newImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < newHeight; i++) {
			for (int j = 0; j < newWidth; j++) {
				int r = data[j][newHeight - 1 - i][0];
				int g = data[j][newHeight - 1 - i][1];
				int b = data[j][newHeight - 1 - i][2];
				int color = getColor(r, g, b);
				newImg.setRGB(j, i, color);
			}
		}
		return newImg;
	}

	static BufferedImage rotate180(int[][][] data) {
		int newHeight = data.length;
		int newWidth = data[0].length;
		BufferedImage newImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < newHeight; i++) {
			for (int j = 0; j < newWidth; j++) {
				int r = data[newHeight - 1 - i][newWidth - 1 - j][0];
				int g = data[newHeight - 1 - i][newWidth - 1 - j][1];
				int b = data[newHeight - 1 - i][newWidth - 1 - j][2];
				int color = getColor(r, g, b);
				newImg.setRGB(j, i, color);
			}
		}
		return newImg;
	}

	static double linear(double x, double value1, double value2) {
		double x1 = Math.floor(x);
		return (x - x1) * (value2 - value1) + value1;
	}

	static int bilinear(double x, double y, int[][][] data, int rgbOption) {
		int height = data.length;
		int width = data[0].length;

		int ceilX = checkBound((int) Math.ceil(x), width);
		int floorX = checkBound((int) Math.floor(x), width);
		int ceilY = checkBound((int) Math.ceil(y), height);
		int floorY = checkBound((int) Math.floor(y), height);

		int leftUp = data[floorY][floorX][rgbOption];
		int rightUp = data[floorY][ceilX][rgbOption];
		int leftDown = data[ceilY][floorX][rgbOption];
		int rightDown = data[ceilY][ceilX][rgbOption];

		double temp1 = linear(x, leftUp, rightUp);
		double temp2 = linear(x, leftDown, rightDown);
		int result = (int) Math.round(linear(y, temp1, temp2));
		return result;
	}

	// 矩陣乘法運算
	static double[] transform(int x, int y, double[][] affineMatrix) {
		int[] location = { x, y, 1 };
		double[] result = new double[3];

		for (int i = 0; i < 3; i++) {
			double sum = 0;
			for (int j = 0; j < 3; j++) {
				sum += affineMatrix[i][j] * location[j];
			}
			result[i] = sum;
		}
		return result;
	}

	// 平移
	static BufferedImage translation(int[][][] data, int deltaX, int deltaY, int bgColor) {
		int height = data.length;
		int width = data[0].length;
		int newHeight = height + deltaY;
		int newWidth = width + deltaX;
		BufferedImage newImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		double[][] affineMatrix = { { 1, 0, deltaX }, { 0, 1, deltaY }, { 0, 0, 1 } };

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				double[] newLocation = transform(j, i, affineMatrix);
				int color = getColor(data[i][j][0], data[i][j][1], data[i][j][2]);
				newImg.setRGB((int) newLocation[0], (int) newLocation[1], color);
			}
		}

		for (int i = 0; i < newHeight; i++) {
			for (int j = 0; j < newWidth; j++) {
				if (newImg.getRGB(j, i) == 0) {
					newImg.setRGB(j, i, bgColor);
				}
			}
		}

		return newImg;
	}

	// 縮放
	static BufferedImage scale(int[][][] data, double ampX, double ampY) {
		int height = data.length;
		int width = data[0].length;
		int newHeight = (int) Math.round(height * ampY);
		int newWidth = (int) Math.round(width * ampX);
		BufferedImage newImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		double[][] affineMatrix = { { (1 / ampX), 0, 0 }, { 0, (1 / ampY), 0 }, { 0, 0, 1 } };

		for (int i = 0; i < newHeight; i++) {
			for (int j = 0; j < newWidth; j++) {
				double[] newLocation = transform(j, i, affineMatrix);
				int r = bilinear(newLocation[0], newLocation[1], data, 0);
				int g = bilinear(newLocation[0], newLocation[1], data, 1);
				int b = bilinear(newLocation[0], newLocation[1], data, 2);
				int color = getColor(r, g, b);
				newImg.setRGB(j, i, color);
			}
		}

		return newImg;
	}

	// 切移
	static BufferedImage shear(int[][][] data, double shearX, double shearY, int bgColor) {
		int height = data.length;
		int width = data[0].length;
		int newHeight = (int) Math.round((width * shearX) + height);
		int newWidth = (int) Math.round((height * shearY) + width);
		BufferedImage newImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		double[][] affineMatrix = { { -1 / (shearX * shearY - 1), shearY / (shearX * shearY - 1), 0 },
				{ shearX / (shearX * shearY - 1), -1 / (shearX * shearY - 1), 0 }, { 0, 0, 1 } };

		for (int i = 0; i < newHeight; i++) {
			for (int j = 0; j < newWidth; j++) {
				double[] newLocation = transform(j, i, affineMatrix);
				if (isOutOfBound(newLocation[0], width) || isOutOfBound(newLocation[1], height)) {
					newImg.setRGB(j, i, bgColor);
				} else {
					int r = bilinear(newLocation[0], newLocation[1], data, 0);
					int g = bilinear(newLocation[0], newLocation[1], data, 1);
					int b = bilinear(newLocation[0], newLocation[1], data, 2);
					int color = getColor(r, g, b);
					newImg.setRGB(j, i, color);
				}
			}
		}

		return newImg;
	}

	// 任意角度旋轉
	static BufferedImage rotate(int[][][] data, double angle, int bgColor) {
		int height = data.length;
		int width = data[0].length;
		double radians = Math.toRadians(angle);
		double sinValue = Math.sin(radians);
		double cosValue = Math.cos(radians);
		int centerX = (int) Math.floor((width - 1) / 2);
		int centerY = (int) Math.floor((height - 1) / 2);
		int newHeight = (int) Math.round((Math.abs(sinValue) * width) + (Math.abs(cosValue) * height));
		int newWidth = (int) Math.round((Math.abs(sinValue) * height) + (Math.abs(cosValue) * width));
		int newCenterX = (int) Math.floor((newWidth - 1) / 2);
		int newCenterY = (int) Math.floor((newHeight - 1) / 2);
		BufferedImage newImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		double[][] affineMatrix = { { (Math.cos(2 * radians) + 1) / (2 * cosValue), -sinValue, 0 },
				{ sinValue, cosValue, 0 }, { 0, 0, 1 } };

		for (int i = 0; i < newHeight; i++) {
			for (int j = 0; j < newWidth; j++) {
				double[] newLocation = transform(j - newCenterX, i - newCenterY, affineMatrix);
				if (isOutOfBound(newLocation[0] + centerX, width) || isOutOfBound(newLocation[1] + centerY, height)) {
					newImg.setRGB(j, i, bgColor);
				} else {
					int r = bilinear(newLocation[0] + centerX, newLocation[1] + centerY, data, 0);
					int g = bilinear(newLocation[0] + centerX, newLocation[1] + centerY, data, 1);
					int b = bilinear(newLocation[0] + centerX, newLocation[1] + centerY, data, 2);
					int color = getColor(r, g, b);
					newImg.setRGB(j, i, color);
				}
			}
		}

		return newImg;
	}

	// RGB轉成HSL
	static double[] convertRGBIntoHSL(int R, int G, int B) {
		double r = R / 255.0;
		double g = G / 255.0;
		double b = B / 255.0;
		double max = Math.max(r, g);
		max = Math.max(max, b);
		double min = Math.min(r, g);
		min = Math.min(min, b);
		double delta = max - min;
		double H = 0;
		double S = 0;
		double L = 0;

		L = (max + min) / 2;

		if (delta == 0) {
			H = 0;
		} else if (max == r) {
			H = ((g - b) / delta % 6) * 60;
		} else if (max == g) {
			H = ((b - r) / delta + 2) * 60;
		} else if (max == b) {
			H = ((r - g) / delta + 4) * 60;
		}

		if (delta == 0) {
			S = 0;
		} else {
			S = delta / (1 - Math.abs(2 * L - 1));
		}

		double[] HSL = new double[3];
		HSL[0] = Math.round(H);
		HSL[1] = Math.round(S * 1000) / 1000.0;
		HSL[2] = Math.round(L * 1000) / 1000.0;
		return HSL;
	}

	// HSL轉成RGB
	static int[] convertHSLIntoRGB(int H, double S, double L) {
		while (H < 0) {
			H += 360;
		}
		while (H >= 360) {
			H -= 360;
		}

		if (S < 0.0) {
			S = 0.0;
		} else if (S > 1.0) {
			S = 1.0;
		}

		if (L < 0.0) {
			L = 0.0;
		} else if (L > 1.0) {
			L = 1.0;
		}

		double C = (1 - Math.abs(2 * L - 1)) * S;
		double X = C * (1 - Math.abs((H / 60.0) % 2 - 1));
		double m = L - C / 2.0;
		double r = 0;
		double g = 0;
		double b = 0;

		if (H >= 0 && H < 60) {
			r = C;
			g = X;
			b = 0;
		} else if (H >= 60 && H < 120) {
			r = X;
			g = C;
			b = 0;
		} else if (H >= 120 && H < 180) {
			r = 0;
			g = C;
			b = X;
		} else if (H >= 180 && H < 240) {
			r = 0;
			g = X;
			b = C;
		} else if (H >= 240 && H < 300) {
			r = X;
			g = 0;
			b = C;
		} else if (H >= 300 && H < 360) {
			r = C;
			g = 0;
			b = X;
		}

		int[] RGB = new int[3];
		RGB[0] = (int) Math.round((r + m) * 255);
		RGB[1] = (int) Math.round((g + m) * 255);
		RGB[2] = (int) Math.round((b + m) * 255);
		return RGB;
	}

	// 取中位數及其座標
	static int[] getMedian(int x, int y, int[][] gray) {
		int height = gray.length;
		int width = gray[0].length;
		List<Integer> neighbors = new ArrayList<>();
		List<Integer> xIndex = new ArrayList<>();
		List<Integer> yIndex = new ArrayList<>();

		for (int i = y - 1; i <= y + 1; i++) {
			for (int j = x - 1; j <= x + 1; j++) {
				if (isOutOfBound(i, height) || isOutOfBound(j, width)) {
					continue;
				} else {
					neighbors.add(gray[i][j]);
					xIndex.add(j);
					yIndex.add(i);
				}
			}
		}

		int[] result = new int[3];
		Collections.sort(neighbors);
		if (neighbors.size() % 2 == 0) {
			int index = neighbors.size() / 2;
			result[0] = (neighbors.get(index) + neighbors.get(index - 1)) / 2;
		} else {
			int index = neighbors.size() / 2;
			result[0] = neighbors.get(index);
		}
		for (int i = 0; i < xIndex.size(); i++) {
			if (gray[yIndex.get(i)][xIndex.get(i)] == result[0]) {
				result[1] = xIndex.get(i);
				result[2] = yIndex.get(i);
				break;
			}
		}
		return result;
	}

	// 取得經過低通濾波器的RGB值
	static int[] getLowPassData(int x, int y, int[][][] data) {
		int height = data.length;
		int width = data[0].length;
		List<Integer> neighbors = new ArrayList<>();
		int[] result = new int[3];

		for (int i = 0; i < 3; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				for (int k = x - 1; k <= x + 1; k++) {
					if (isOutOfBound(j, height) || isOutOfBound(k, width)) {
						continue;
					} else {
						neighbors.add(data[j][k][i]);
					}
				}
			}
			int sum = 0;
			for (int value : neighbors) {
				sum += value;
			}
			int size = neighbors.size();
			result[i] = Math.round((float) (sum / size));
			neighbors.clear();
		}
		return result;
	}

	// 取得經過高通濾波器的RGB值
	static int[] getHighPassData(int x, int y, int[][][] data) {
		int height = data.length;
		int width = data[0].length;
		List<Integer> neighbors = new ArrayList<>();
		int[] result = new int[3];

		for (int i = 0; i < 3; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				for (int k = x - 1; k <= x + 1; k++) {
					if (isOutOfBound(j, height) || isOutOfBound(k, width)) {
						continue;
					} else if (j == y && k == x) {
						continue;
					} else {
						neighbors.add(data[j][k][i]);
					}
				}
			}
			double sum = 0;
			int size = neighbors.size() + 1;
			for (int value : neighbors) {
				sum += value;
			}
			sum *= -(1.0 / size);
			sum += (double) ((size - 1) * data[y][x][i] / size);
			result[i] = (int) Math.round(data[y][x][i] + sum);
			neighbors.clear();
		}
		return result;
	}

	static int getSlope(int x, int y, int[][] gray) {
		int height = gray.length;
		int width = gray[0].length;
		int xValue;
		int yValue;

		if (isOutOfBound(x + 1, width)) {
			xValue = gray[y][x - 1];
		} else {
			xValue = gray[y][x + 1];
		}

		if (isOutOfBound(y + 1, height)) {
			yValue = gray[y - 1][x];
		} else {
			yValue = gray[y + 1][x];
		}

		return (int) (Math.pow(gray[y][x] - xValue, 2) + Math.pow(gray[y][x] - yValue, 2));
	}
}
