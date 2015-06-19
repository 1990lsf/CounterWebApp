package com.org.testUtil;

import javax.swing.JOptionPane;

public class TestI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int i=4;
		System.out.println(i-=i*=i+=i);
		String s=JOptionPane.showInputDialog("给一个不多于5位的正整数:\n").trim();
        JOptionPane.showMessageDialog(null, s.length());
        for(int k=s.length()-1;k>=0;k--){
             System.out.print(s.charAt(k)+" ");
        }

	}

}
