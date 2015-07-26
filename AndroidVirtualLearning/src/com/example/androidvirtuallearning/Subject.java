package com.example.androidvirtuallearning;

import java.util.ArrayList;
import java.util.Date;

public class Subject 
{
	private String code, name, instructor, schedule;
	private Date startTime, endTime;
	
	private ArrayList<Integer> textLectures;
	private ArrayList<Integer> imgLectures;
	private ArrayList<Integer> videoPaths;
	private ArrayList<Integer> pdfPaths;
	private ArrayList<Integer> quizes;
	
	private String[] videoNames;
	private String[] imageNames;
	private String[] pdfNames;
	private String[] txtLecturesNames;
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	
	public void AddTextLecture(Integer text)
	{
		if(textLectures == null)
		{
			textLectures = new ArrayList<Integer>();
		}
		
		textLectures.add(text);
	}
	
	public ArrayList<Integer> getTextLectures() {
		return textLectures;
	}
	public void setTextLectures(ArrayList<Integer> textLectures) {
		this.textLectures = textLectures;
	}
	
	public void AddImageLecture(Integer img)
	{
		if(imgLectures == null)
		{
			imgLectures = new ArrayList<Integer>();
		}
		
		imgLectures.add(img);
	}
	
	public ArrayList<Integer> getImgLectures() {
		return imgLectures;
	}
	public void setImgLectures(ArrayList<Integer> imgLectures) {
		this.imgLectures = imgLectures;
	}
	
	public void AddVideoPath(Integer path)
	{
		if(videoPaths == null)
		{
			videoPaths = new ArrayList<Integer>();
		}
		
		videoPaths.add(path);
	}
	
	public ArrayList<Integer> getVideoPaths() {
		return videoPaths;
	}
	public void setVideoPaths(ArrayList<Integer> videoPaths) {
		this.videoPaths = videoPaths;
	}
	
	public void AddPdfPath(Integer path)
	{
		if(pdfPaths == null)
		{
			pdfPaths = new ArrayList<Integer>();
		}
		
		pdfPaths.add(path);
		
	}
	
	public ArrayList<Integer> getPdfPaths() {
		return pdfPaths;
	}
	public void setPdfPaths(ArrayList<Integer> pdfPaths) {
		this.pdfPaths = pdfPaths;
	}
	public ArrayList<Integer> getQuizes() {
		return quizes;
	}
	public void setQuizes(ArrayList<Integer> quizes) {
		this.quizes = quizes;
	}
	public String[] getVideoNames() {
		
		return videoNames;
	}
	public void setVideoNames(String[] videoNames) {
		this.videoNames = videoNames;
		
	}
	public String[] getImageNames() {
		return imageNames;
	}
	public void setImageNames(String[] imageNames) {
		this.imageNames = imageNames;
	}
	public String[] getPdfNames() {
		return pdfNames;
	}
	public void setPdfNames(String[] pdfNames) {
		this.pdfNames = pdfNames;
	}
	public String[] getTxtLecturesNames() {
		return txtLecturesNames;
	}
	public void setTxtLecturesNames(String[] txtLecturesNames) {
		this.txtLecturesNames = txtLecturesNames;
	}
}
