package org.drinking.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class DrawUtils {

	static final int LINES = 10, COLUMNS = 9;

	public int getChessSize() {
		final int CHESSINROW = 9;
		return screenWidth / CHESSINROW;
	}

	public Point getBoardStartPoint() {
		Point point = new Point();
		int chessSize = getChessSize();
		point.x = (screenWidth-8*chessSize)/2;
		point.y = ((screenHeight - 9 * chessSize) / 2);
		return point;
	}

	/**
	 * @return one of the point in 9x10 board
	 */
	public Point getBoardPoint(int row, int col) {
		Point start = getBoardStartPoint();
		int chessSize = getChessSize();
		Point point = new Point();
		point.x = col * chessSize + start.x;
		point.y = row * chessSize + start.y;
		return point;
	}

	public Bitmap drawChessboard() {
		drawLines();
		draw2X();
		drawCross();
		return chessBoard;
	}

	private void drawLines() {
		int boardX = getBoardStartPoint().x;
		int boardY = getBoardStartPoint().y;

		// draw columns
		for (int i = 0, x = boardX; i < COLUMNS; i++, x += chessSize) {
			if (i == 0 || i == COLUMNS - 1) {
				int DESY = boardY + 9 * chessSize;
				canvas.drawLine(x, boardY, x, DESY, paint);
			} else {
				int DESY = boardY + 4 * chessSize;
				canvas.drawLine(x, boardY, x, DESY, paint);
				canvas.drawLine(x, DESY + chessSize, x, DESY + 5 * chessSize,
						paint);
			}
		}
		// draw lines
		for (int i = 0, y = boardY, DESX = boardX + 8 * chessSize; i < LINES; y += chessSize, i++) {
			canvas.drawLine(boardX, y, DESX, y, paint);
		}

	}

	private void draw2X() {
		int pointxy[] = new int[] { 0, 3, 2, 5, 2, 3, 0, 5, 7, 3, 9, 5, 9, 3,
				7, 5 };
		for (int i = 0; i < pointxy.length; i += 4) {
			Point start = getBoardPoint(pointxy[i], pointxy[i + 1]);
			Point end = getBoardPoint(pointxy[i + 2], pointxy[i + 3]);
			canvas.drawLine(start.x, start.y, end.x, end.y, paint);
		}
	}

	private void drawCross() {
		final int pad=chessSize/13;
		final int dashLen=chessSize/7;
		int crosses[]=new int[]{2,1,2,7,3,0,3,2,3,4,3,6,3,8,6,0,6,2,6,4,6,6,6,8,7,1,7,7};
		Point point;
		for(int i=0;i<crosses.length;i+=2){
			point=getBoardPoint(crosses[i], crosses[i+1]);
			if(crosses[i+1]!=0){
				//left up
				canvas.drawLine(point.x-pad, point.y-pad, point.x-pad, point.y-pad-dashLen, paint);
				canvas.drawLine(point.x-pad, point.y-pad, point.x-pad-dashLen, point.y-pad, paint);
				//left down
				canvas.drawLine(point.x-pad, point.y+pad, point.x-pad, point.y+pad+dashLen, paint);
				canvas.drawLine(point.x-pad, point.y+pad, point.x-pad-dashLen, point.y+pad, paint);
			}
			if(crosses[i+1]!=8){
				//right up
				canvas.drawLine(point.x+pad, point.y-pad, point.x+pad, point.y-pad-dashLen, paint);
				canvas.drawLine(point.x+pad, point.y-pad, point.x+pad+dashLen, point.y-pad, paint);
				//right down
				canvas.drawLine(point.x+pad, point.y+pad, point.x+pad+dashLen, point.y+pad, paint);
				canvas.drawLine(point.x+pad, point.y+pad, point.x+pad, point.y+pad+dashLen, paint);
			}
		}
		
	}

	int screenWidth, screenHeight;
	Paint paint;
	Bitmap chessBoard;
	Canvas canvas;
	int chessSize;

	public DrawUtils(int sw, int sh) {
		screenWidth = sw;
		screenHeight = sh;
		chessSize = getChessSize();
		paint = new Paint();
		paint.setStrokeWidth(1);
		paint.setColor(Color.BLACK);
		chessBoard = Bitmap.createBitmap(screenWidth, screenHeight,
				Config.ARGB_8888);
		canvas = new Canvas(chessBoard);
	}
}
