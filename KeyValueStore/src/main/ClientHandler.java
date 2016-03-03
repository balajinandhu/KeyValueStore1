package main;
import java.util.*;
import java.lang.*;
import java.math.BigInteger;
import java.io.*;
import java.net.*;

public class ClientHandler extends Thread{
	Socket server;
	int port;
	PeerVar parameters;
	//Map<BigInteger,Successor> fingerTable;
	ClientHandler(Socket conn,int port,PeerVar parameters){
		this.server=conn;
		this.port=port;
		this.parameters=parameters;
	}
	
	public void run(){
		System.out.println("Just connected to "
                + server.getRemoteSocketAddress());
         DataInputStream in = null;
		try {
			in = new DataInputStream(server.getInputStream());
			
			String recevied = in.readUTF();
			
			
			
			
			System.out.println("recevied msg: "+recevied);
			
			
			DataOutputStream out = null;
			
			out = new DataOutputStream(server.getOutputStream());
			
			
			out.writeUTF("Thank you for connecting to "
				    + server.getLocalSocketAddress() + "\nGoodbye!");
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String closestPrecedingFinger(BigInteger id){
		for(int i=Vars.m-1;i>=0;i--){
			if(Vars.isInRange(false, false, parameters.nodeName, id, parameters.fingerTable.get(i).node)){
				return parameters.fingerTable.get(i).node.toString()+" "+ String.valueOf(parameters.fingerTable.get(i).port);
			}
		}
		return parameters.nodeName.toString()+" "+this.parameters.port;
	} 
	
	
	public String findPredecessor(BigInteger id){
		
		if(parameters.nodeName.compareTo(parameters.succ)==0)
			return parameters.nodeName.toString()+" "+parameters.port;
		BigInteger nodePrime=parameters.nodeName;
		// Here we need to connect to the server of succs
		BigInteger nodePrimeSucc=parameters.succ;
		String temp="";
		OurRMI ourRMI;
		String resultPort="";
		while(!Vars.isInRange(false, true, nodePrime, nodePrimeSucc, id)){
			if(nodePrime.compareTo(parameters.nodeName)==0){
				temp=this.closestPrecedingFinger(id);
			}
			else{
					ourRMI = new OurRMI(Integer.parseInt(temp.split(" ")[1]),"closestPrecedingFinger:"+id.toString()+": "+": "+": ");
					temp=ourRMI.result();
			}
			nodePrime=new BigInteger(temp.split(" ")[0]);
			resultPort=temp.split(" ")[1];
			if(nodePrime.compareTo(parameters.nodeName)!=0){
				   ourRMI=new OurRMI(Integer.parseInt(temp.split(" ")[1]),"findSuccessor:"+id.toString()+": "+": "+": ");
				   nodePrimeSucc=new BigInteger(ourRMI.result().split(" ")[0]);	   
			}
			else
				break;
		}
		return nodePrime.toString()+" "+resultPort;
	}
	
	public String findSuccessor(BigInteger id){
		OurRMI ourRMI = new OurRMI(Integer.parseInt(findPredecessor(id).split(" ")[1]),"findSuccessor:"+id.toString()+": "+": "+": ");
		return ourRMI.result();
	}
	
	
	public void join(int friend) {
		boolean first=false;
		Socket  client;
		try {
			client=new Socket("localhost",friend);
			} catch (UnknownHostException e) {
				for(int i=0;i<Vars.m;i++){
				//	parameters.fingerTable.add(new Successor())
				}
				first=true;
				e.printStackTrace();
				 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
	
	
}
