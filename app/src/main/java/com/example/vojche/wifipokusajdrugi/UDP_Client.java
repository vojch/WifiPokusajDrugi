package com.example.vojche.wifipokusajdrugi;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;

public class UDP_Client {

	// protected static final InetAddress IP = "192.168.1.5";

	private AsyncTask<Void, Void, Void> async_cient;
	public String Message;
	public int port;
	public String IPs;

//	int port = 7000;

	@SuppressLint("NewApi")
	public void NachrichtSenden() {
		async_cient = new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				DatagramSocket ds = null;

				try {

					ds = new DatagramSocket();
					DatagramPacket dp;
					InetAddress IP = InetAddress.getByName(IPs);
//					InetAddress IP = InetAddress.getByName("192.168.1.5");
					dp = new DatagramPacket(Message.getBytes(),
							Message.length(), IP, port);

					ds.setBroadcast(true);
					ds.send(dp);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (ds != null) {
						ds.close();
					}
				}
				return null;
			}

			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
			}
		};

		if (Build.VERSION.SDK_INT >= 11)
			async_cient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			async_cient.execute();
	}
}