package com.example.wps.nfc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;

/**
 * Read NFC tag in a different thread.
 */
public class NfcReader extends Thread {
	
	private Tag tag;
	public byte[] data;
	private static final int dataLength = 16;
	
	public NfcReader(Tag tag) {
		this.tag = tag;
		this.data = null;
	}

	@Override
	public void run() {
		super.run();
		MifareUltralight ultralight = MifareUltralight.get(tag);
		List<Byte> workingData = new ArrayList<>();
		try {
			ultralight.connect();
			for (int i=0; i<dataLength; i+=16) {
				byte[] currentArray = ultralight.readPages(4+(i/16));
				for (int j=0; j<currentArray.length; ++j) {
					workingData.add(currentArray[j]);
				}
			}
			Byte[] workingData2 = new Byte[workingData.size()];
			data = ArrayUtils.toPrimitive(workingData.toArray(workingData2));
			System.out.println("Reading completed");
			ultralight.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
