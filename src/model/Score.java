package model;

public class Score {
	String methodologyName,answerString="";
	int maxScoreReached=0;
	public int numberOfLibsSignedUp=0;
	@Override
	public String toString() {
		return "Score [methodologyName: "+methodologyName+", maxScoreReached: " +maxScoreReached +"]";
	}
	public String getMethodologyName() {
		return methodologyName;
	}
	public void setMethodologyName(String methodologyName) {
		this.methodologyName = methodologyName;
	}
	public String getAnswerString() {
		return answerString;
	}
	public void setAnswerString(String answerString) {
		this.answerString = answerString;
	}
	public int getMaxScoreReached() {
		return maxScoreReached;
	}
	public void setMaxScoreReached(int maxScoreReached) {
		this.maxScoreReached = maxScoreReached;
	}
	public Score(String answerString, int maxScoreReached) {
		super();
		this.answerString = answerString;
		this.maxScoreReached = maxScoreReached;
	}
	public Score() {
		
	}
	
}
