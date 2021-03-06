package cn.bassy.tools;

import java.util.Scanner;

import cn.bassy.tools.processor.CppCodeProcessor;
import cn.bassy.tools.processor.IProcessor;
import cn.bassy.tools.processor.JavaCodeProcessor;
import cn.bassy.tools.processor.XmlCodeProcessor;
import cn.bassy.tools.utils.ClipboardHelper;
import cn.bassy.tools.utils.Log;
import cn.bassy.tools.xml.OnResultListener;
import cn.bassy.tools.xml.XMLHelper;

public class Main {

	public static void main(String[] args) {

		Scanner scanner = new Scanner( System.in );
		Log.setDebug(false);
		
		while (true) {

			showDescription();

			int cmd = getCommand(scanner);

			IProcessor processor = null;
			switch (cmd) {
			case 1:
				processor = new JavaCodeProcessor();
				break;
			case 2:
				processor = new XmlCodeProcessor();
				break;
			case 3:
				processor = new CppCodeProcessor();
				break;
			default:
				System.out.println(cmd + "指令暂时不被支持，请重新输入！");
				continue;
			}

			String content = ClipboardHelper.getSysClipboardHTMLText();
			if (content == null || content.length() <= 0) {
				continue;
			} else {
				System.out.println("待处理的内容\n" + content);
				XMLHelper.parse(content, processor, mOnResultListener);
			}
		}
	}

	/**
	 * 显示使用帮助
	 */
	private static void showDescription() {
		System.out.println("---------------------------------------------");
		System.out.println("|　　　　　　　　　　　　　　　　　　　 ");
		System.out.println("|　　　　新浪博客代码转换器　　　　　　 ");
		System.out.println("|　　　　　　　　　　　　　　　　　　　 ");
		System.out.println("|使用说明：　　　　　　　　　　　　　　 ");
		System.out.println("|1、从Android Studio中复制代码　　　　");
		System.out.println("|2、根据编号输入对应代码类型");
		System.out.println("|3、程序将自动从剪贴板获取内容进行转换");
		System.out.println("|4、转换结束之后，剪贴板的内容就是结果 ");
		System.out.println("|　　　　　　　　　　　　　　　　　　　 ");
		System.out.println("|注意：对于含有“<”的情况，需要在切换到源，然后再粘贴");
		System.out.println("|　　　　　　　　　　　　　　　　　　　 ");
		System.out.println("|1：Java；2：XML；3、C++　　　　　　　　　　　　 ");
		System.out.println("---------------------------------------------");

	}

	/**
	 * 获取用户输入的命令
	 */
	private static int getCommand(Scanner scanner) {
		byte[] input = new byte[10];
		int cmd = 0;

		try {
			System.out.println("请输入指令：");
			cmd = scanner.nextInt();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(new String(input) + "指令错误，请重新输入！");
		}
		return cmd;
	}

	static OnResultListener mOnResultListener = new OnResultListener() {

		@Override
		public void onResult(String result) {
			ClipboardHelper.setTextToSysClipboard(result);
			System.out.println("处理完毕\n" + result);
			System.out.println("内容已经复制到剪贴板，请测试");
		}

		@Override
		public void onError(Exception e) {
			System.out.println("解析出错了：" + e.getMessage());
		}
	};

}
