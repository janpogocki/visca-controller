package hello;

import jssc.SerialPortException;
import hello.command.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) {
		ViscaCtrl viscaCtrl = new ViscaCtrl();

		try {
			viscaCtrl.init();
		} catch (SerialPortException e) {
			e.printStackTrace();
		}

		BufferedReader buffer = new BufferedReader(new InputStreamReader(
				System.in));
		String line = null;

		do {
			try {
				line = buffer.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				System.out.println(viscaCtrl.executeCommand(line));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} while (line.equals("close") == false);

		try {
			viscaCtrl.closeSerial();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
