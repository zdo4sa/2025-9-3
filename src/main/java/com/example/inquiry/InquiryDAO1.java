package com.example.inquiry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class InquiryDAO1 {
	private static final List<Inquiry1> inquiries1 = Collections.synchronizedList(new ArrayList<>());
	public void addInquiry1(Inquiry1 inquiry1) {
		inquiries1.add(inquiry1);
	}
	public List<Inquiry1> getAllInquiries1() {
		return new ArrayList<>(inquiries1);
	}
	public Inquiry1 getInquiry1ByIndex(int index) {
		if (index >= 0 && index < inquiries1.size()) {
			return inquiries1.get(index);
		}
		return null;
	}
	public boolean updateInquiry1Status(int index, String newStatu) {
		if (index >= 0 && index < inquiries1.size()) {
			inquiries1.get(index).setStatus(newStatu);
			return true;
		}
		return false;
	}
}