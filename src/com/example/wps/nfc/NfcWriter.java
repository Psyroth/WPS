package com.example.wps.nfc;

import java.io.IOException;
import java.util.Arrays;

import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;

public class NfcWriter extends Thread {
	
	private Tag tag;
	private byte[] data;
	private static final int dataLength = 16;

	public NfcWriter(Tag tag, byte[] data) throws Exception {
		this.tag = tag;
		if (data.length != dataLength) {
			throw new Exception("Invalid number of bytes : should be of length " + String.valueOf(dataLength));
		}
		this.data = data;
	}
	
	@Override
	public void run() {
		super.run();
		MifareUltralight ultralight = MifareUltralight.get(tag);
		try {
			ultralight.connect();
			for (int i=0; i<dataLength; i+=4) {
				byte[] currentArray = Arrays.copyOfRange(data, i, i+4);
				ultralight.writePage(4+(i/4), currentArray);
			}
			System.out.println("Writing completed");
			ultralight.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
