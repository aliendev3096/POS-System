package test.seis602.pos;

import org.junit.*;
import main.seis602.pos.register.Refund;

public class RefundTest {
	
	@Test
	public void Initialize_Refund() throws Exception
	{

		Refund refund = new Refund();
		refund.addItem("Orange");
		refund.setRefundAmount(3.99);
		refund.setSaleId(1);
		
		Assert.assertEquals(3.99, refund.getRefundAmount(), .01);
		Assert.assertEquals(1, refund.getItems().size());
		Assert.assertEquals(1, refund.getSaleId());
	}
	
}
