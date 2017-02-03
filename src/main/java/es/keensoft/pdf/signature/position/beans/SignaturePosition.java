package es.keensoft.pdf.signature.position.beans;

public class SignaturePosition {
	private Integer page;
	private Float lowerLeftX;
	private Float lowerLeftY;
	private Float upperRightX;
	private Float upperRightY;
	private Float margin;

	public SignaturePosition() {
		super();
	}

	public SignaturePosition(Integer page, Float lowerLeftX, Float lowerLeftY, Float upperRightX, Float upperRightY, Float margin) {
		super();
		this.page = page;
		this.lowerLeftX = lowerLeftX;
		this.lowerLeftY = lowerLeftY;
		this.upperRightX = upperRightX;
		this.upperRightY = upperRightY;
		this.margin = margin;
	}

	public SignaturePosition merge(SignaturePosition positionToMerge) {
		SignaturePosition result = new SignaturePosition();
		if (positionToMerge != null) {
			result.page = positionToMerge.page != null ? positionToMerge.page : page;
			result.lowerLeftX = positionToMerge.lowerLeftX != null ? positionToMerge.lowerLeftX : lowerLeftX;
			result.lowerLeftY = positionToMerge.lowerLeftY != null ? positionToMerge.lowerLeftY : lowerLeftY;
			result.upperRightX = positionToMerge.upperRightX != null ? positionToMerge.upperRightX : upperRightX;
			result.upperRightY = positionToMerge.upperRightY != null ? positionToMerge.upperRightY : upperRightY;
			result.margin = positionToMerge.margin != null ? positionToMerge.margin : margin;
		} else {
			result.page = page;
			result.lowerLeftX = lowerLeftX;
			result.lowerLeftY = lowerLeftY;
			result.upperRightX = upperRightX;
			result.upperRightY = upperRightY;
			result.margin = margin;
		}
		return result;
	}

	public Integer getPage() {
		return page;
	}

	public Float getLowerLeftX() {
		return lowerLeftX;
	}

	public void setLowerLeftX(Float lowerLeftX) {
		this.lowerLeftX = lowerLeftX;
	}

	public Float getLowerLeftY() {
		return lowerLeftY;
	}

	public void setLowerLeftY(Float lowerLeftY) {
		this.lowerLeftY = lowerLeftY;
	}

	public Float getUpperRightX() {
		return upperRightX;
	}

	public void setUpperRightX(Float upperRightX) {
		this.upperRightX = upperRightX;
	}

	public Float getUpperRightY() {
		return upperRightY;
	}

	public void setUpperRightY(Float upperRightY) {
		this.upperRightY = upperRightY;
	}

	public Float getMargin() {
		return margin;
	}

	public void setMargin(Float margin) {
		this.margin = margin;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "SignaturePosition [page=" + page + ", lowerLeftX=" + lowerLeftX + ", lowerLeftY=" + lowerLeftY + ", upperRightX=" + upperRightX + ", upperRightY=" + upperRightY + ", margin=" + margin + "]";
	}
}
