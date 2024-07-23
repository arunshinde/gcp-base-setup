package com.gcp.cloud.firestore;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentChange;
import com.google.cloud.firestore.ListenerRegistration;

public class FirestoreListeners {
	
	static {
		listenFirestoreCollection(); 
	}
	
	/**
	 * The sample collection node where snapshot will be attached and realtime data will be read 
	 * @author arun
	 */
	private static void listenFirestoreCollection() {
        CollectionReference collectionRef = FirestoreUtility.getInstance().firestore.collection("users");
        ListenerRegistration registration = collectionRef.addSnapshotListener((snapshot, error) -> {
            if (error != null) {
                System.err.println("Listen failed: " + error);
                return;
            }
            for (DocumentChange dc : snapshot.getDocumentChanges()) {
            	System.out.println("Document ID:" + dc.getDocument().getId());
                switch (dc.getType()) {
                    case ADDED:
                        System.out.println("New document: " + dc.getDocument().getData());
                        break;
                    case MODIFIED:
                        System.out.println("Modified document: " + dc.getDocument().getData());
                        break;
                    case REMOVED:
                        System.out.println("Removed document: " + dc.getDocument().getId());
                        break;
                }
            }
        });
	}
}
