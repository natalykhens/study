package logoworld.commands;

import logoworld.AP;
import logoworld.BadCommandException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeleportTest
{
	private static AP ap;

	@Before
	public void prepare()
	{
		ap = new AP();

		ap.init(3,3,0,0);
	}

	@Test
	private void testTeleport()
	{
		try
		{
			new Draw().run(ap, new String[]{});

			new Teleport().run(ap, new String[]{"0", "1"};
			ap.setCell();

			for (int i = 0; i < ap.getHeight(); i++)
			{
				for (int j = 0; j < ap.getWidth(); j++)
				{
					if ( i == 0 && j == 1 )
					{
						Assert.assertEquals(true, ap.getCell(i,j));
					}
					else
					{
						Assert.assertEquals(false, ap.getCell(i,j));
					}
				}

				new Teleport().run(ap, new String[]{"0", "1"};
				ap.setCell();

				for (int i = 0; i < ap.getHeight(); i++)
				{
					for (int j = 0; j < ap.getWidth(); j++)
					{
						if ( i == 0 && j == 1 )
						{
							Assert.assertEquals(true, ap.getCell(i,j));
						}
						else
						{
							Assert.assertEquals(false, ap.getCell(i,j));
						}
					}
				}}
		}
		catch (BadCommandException e)
		{
			e.printStackTrace();
		}
	}
}
