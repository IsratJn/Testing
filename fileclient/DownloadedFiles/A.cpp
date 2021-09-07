#include<cstdio>
#include<sstream>
#include<cstdlib>
#include<cctype>
#include<cmath>
#include<algorithm>
#include<set>
#include<queue>
#include<stack>
#include<list>
#include<iostream>
#include<fstream>
#include<numeric>
#include<string>
#include<vector>
#include<cstring>
#include<map>
#include<iterator>
#include <iomanip>

using namespace std;

// #include <ext/pb_ds/assoc_container.hpp>
// #include <ext/pb_ds/tree_policy.hpp>
// using namespace __gnu_pbds;
// typedef tree<int,null_type,less<int>,rb_tree_tag, tree_order_statistics_node_update> ordered_set;

map<int,int>m;
int main()
{
	int a,b,mn=10000000;
	cin >> a >> b;
	string s;
	cin >> s;
	sort(s.begin(),s.end());
	for(int i=0;i<s.length();i++)
	{
		m[s[i]-48]++;
	}
	for (int i = 17; i < 17+b; ++i)
	{
		//cout << m[i] << endl;
		if(m[i] < mn)
			mn=m[i];
	}
	cout << mn*b << endl;


}
