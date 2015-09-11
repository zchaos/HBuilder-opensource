package com.aptana.ruby.internal.debug.core.parsing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.aptana.core.util.StringUtil;
import com.aptana.ruby.internal.debug.core.model.ThreadInfo;

@SuppressWarnings("nls")
public class ThreadInfoReader extends XmlStreamReader
{

	private List<ThreadInfo> threads = new ArrayList<ThreadInfo>();

	public ThreadInfoReader(XmlPullParser xpp)
	{
		super(xpp);
	}

	public ThreadInfoReader(AbstractReadStrategy readStrategy)
	{
		super(readStrategy);
	}

	public ThreadInfo[] readThreads() throws XmlPullParserException, IOException, XmlStreamReaderException
	{
		this.read();
		return threads.toArray(new ThreadInfo[threads.size()]);
	}

	protected boolean processStartElement(XmlPullParser xpp)
	{
		String name = xpp.getName();
		if (name.equals("threads"))
		{
			return true;
		}
		if (name.equals("thread"))
		{
			int id = Integer.parseInt(xpp.getAttributeValue(StringUtil.EMPTY, "id"));
			String status = xpp.getAttributeValue(StringUtil.EMPTY, "status");
			threads.add(new ThreadInfo(id, status));
			return true;
		}
		return false;
	}

	protected boolean processEndElement(XmlPullParser xpp)
	{
		return xpp.getName().equals("threads");
	}
}
