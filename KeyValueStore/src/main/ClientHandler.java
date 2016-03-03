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
		Socket  client;
		try {
			client=new Socket("localhost",friend);
			} catch (UnknownHostException e) {
				for(int i=0;i<Vars.m;i++){
					Successor temp=new Successor(new BigInteger("2").pow(i),
							new BigInteger("2").pow(i+1),parameters.nodeName,parameters.port);
					parameters.fingerTable.add(temp);
				}
				parameters.succ=parameters.nodeName;
				parameters.pred=parameters.nodeName;
				parameters.succPort=parameters.port;
				parameters.predPort=parameters.port;
				return;
			//	e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		initFingerTable(friend);
	//  notify();
	//  get successor S
	// move the keys to n
		
	}
	
	
	public void initFingerTable(int port){
		OurRMI ourRMI=new OurRMI(port,"findSuccessor:"+parameters.fingerTable.get(1).intervalStart.toString()+": "+": "+": ");
		String res=ourRMI.result();
		parameters.fingerTable.get(1).node = new BigInteger(res.split(" ")[0]);
		parameters.fingerTable.get(1).port= Integer.parseInt(res.split(" ")[1]);
		
		for(int i=0;i<Vars.m;i++){
			if(Vars.isInRange(true,false,parameters.nodeName, parameters.fingerTable.get(i).node,parameters.fingerTable.get(i+1).intervalStart)){
				parameters.fingerTable.get(i+1).node = parameters.fingerTable.get(i).node;
				parameters.fingerTable.get(i+1).port = parameters.fingerTable.get(i).port;
			}
			else{
				ourRMI=new OurRMI(port,"findSuccessor:"+parameters.fingerTable.get(1).intervalStart.toString()+": "+": "+": ");
				res=ourRMI.result();
				parameters.fingerTable.get(i+1).node = new BigInteger(res.split(" ")[0]);
				parameters.fingerTable.get(i+1).port= Integer.parseInt(res.split(" ")[1]);
			}
		}
	}
	
	
	public void notifyAl(){
		OurRMI ourRMI;
		String res;
		for(int i=0;i<Vars.m;i++){
			res=findPredecessor(parameters.nodeName.subtract(new BigInteger("2").pow(i)));
		}
	}
	
	
	
}
