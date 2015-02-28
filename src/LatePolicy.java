
public class LatePolicy implements ILatePolicy{
	private int daysUntilLate;
	private float costPerDayLate;

	public LatePolicy(int daysUntilLate, float costPerDayLate) {
		this.daysUntilLate = daysUntilLate;
		this.costPerDayLate = costPerDayLate;
	}

	public int getDaysUntilLate() {
		return daysUntilLate;
	}

	public void setDaysUntilLate(int days) {
		daysUntilLate = days;
	}

	public float getCostPerDayLate() {
		return costPerDayLate;
	}

	public void setCostPerDayLate(float cost) {
		costPerDayLate = cost;
	}
}
