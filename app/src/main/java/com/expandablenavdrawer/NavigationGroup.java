package com.expandablenavdrawer;

import java.util.ArrayList;
import java.util.List;

public class NavigationGroup {
	public String Name;
	public List<String> GroupItemCollection;

	public NavigationGroup()
	{
		GroupItemCollection = new ArrayList<String>();
	}
}
