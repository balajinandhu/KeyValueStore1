package main;

import java.io.IOException;
import java.net.UnknownHostException;

public class MasterRun {
	
	public static void main(String[] args) throws UnknownHostException, IOException{
		PeerNode p1=new PeerNode(5710,"C:/Users/srinivasMaram/workspace/KeyValueStore/keys/5678.txt");
		PeerNode p2=new PeerNode(5711,"C:/Users/srinivasMaram/workspace/KeyValueStore/keys/5679.txt");
	//	PeerNode p3=new PeerNode(5681,"C:/Users/srinivasMaram/workspace/KeyValueStore/keys/5680.txt");
		
		
		OurRMI ourRMI = new OurRMI(5710, "join"+":5623"+":"+":");
		ourRMI.result();
		
		ourRMI = new OurRMI(5711, "join"+":5710"+":"+":");
		ourRMI.result();
	//	p1.asClient("localhost", 5679);
	//	p2.asClient("localhost", 5680);
	//	p3.asClient("localhost", 5678);
		
		
	//	PeerNode p2=new PeerNode(5679,"c:/Users/srinivasMaram/workspace/KeyValueStore/keys/5679.txt");
		//p2.join(p1);
		
		
		
		
	//	PeerNode p3 = new PeerNode(5679,"c:/Users/srinivasMaram/workspace/KeyValueStore/keys/5680.txt");
		
		
	}
}
