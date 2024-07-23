package com.gcp.cloud.firestore;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FirestoreExecute extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		new FirestoreListeners();
		new FirestoreExecutors().createFirestoreEntityUsingSDK();
		resp.getOutputStream().print("Firestore Testing is completed");
	}
}
