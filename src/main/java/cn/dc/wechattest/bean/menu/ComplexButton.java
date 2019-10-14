package cn.dc.wechattest.bean.menu;
/**
 * 复合类型的按钮
 * @author Administrator
 *
 */
public class ComplexButton extends Button{
		private Button[] Sub_button;

		public Button[] getSub_button() {
			return Sub_button;
		}

		public void setSub_button(Button[] subButton) {
			Sub_button = subButton;
		}
		

	
		
}
