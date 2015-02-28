
public interface ILatePolicy {
	public int getDaysUntilLate();
	public void setDaysUntilLate(int days);
	public float getCostPerDayLate();
	public void setCostPerDayLate(float cost);
}
