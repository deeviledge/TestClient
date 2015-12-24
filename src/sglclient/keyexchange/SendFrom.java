package sglclient.keyexchange;
/**
 * 自分が先に鍵を送る時呼び出す.
 * 公開鍵の交換を行うクラス.
 * 
 * @author nishimura
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;


public class SendFrom {
	private Socket socket;
        private ServerSocket serversoc;
	public  SendFrom(String peerip,int roundport){
		try {
			socket = new Socket( peerip , roundport );
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
        public  SendFrom(String peerip,String serverip,int roundport){
		try {
                    serversoc=new ServerSocket(roundport);
                    socket =serversoc.accept();//待機状態へ移行
                    while(socket.isConnected())
                    {//サーバからの接続要求を待機する
                        System.out.println("Socketからの接続要求を待機中...");
                    }
                    System.out.println("SGLサーバ："+socket.getInetAddress()+"との接続完了");//接続先アドレスを返して表示
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
        
        
        
	/**
	 * 
	 * @param pk
	 * @return 受け取った公開鍵
	 */
	public String KeyExchange(BigInteger pk){
		String line = null;
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//公開鍵を送信
			String input = "pk:";
			input += ""+pk;
			out.println(input);
			//System.out.println("Peer client :" + "送信:" + input);
			//公開鍵を受信
			line = in.readLine();
			//System.out.println("Peer client :" + "受信:" + line);
			socket.close();
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		return line;
		
	}
}