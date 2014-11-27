package viewmodel;

import datamodel_new.WinLostCellData;

public class WinLostCell {
	WinLostCellData cellData;
	SeasonCanvas canvas;
	
	
	public WinLostCell (SeasonCanvas canvas) {
		this.canvas = canvas;
	}
	
	public WinLostCell (SeasonCanvas canvas, WinLostCellData cellData) {
		this(canvas);
		this.cellData = cellData;
	}
	
}
