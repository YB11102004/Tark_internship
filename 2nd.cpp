#include <iostream>
#include <string>
using namespace std;
int main() {
    string s,t;
    int a[26]={0},i;
    cin>>s;
    cin>>t;
    for(i=0;i<t.length();i++)
    {
        int b=t[i]-97;
        a[b]++;
    }
    for (int j=25;j>=0;j--)
    {
        if (a[j]!=0)
        {
            char c=char(j+'a');
            int d=a[j];
            for (int i=0;i<s.length();i++)
            {
                if (d==0)
                {
                    break;
                }
                if(s[i]<c)
                {
                    s[i]=c;
                    d--;
                }
            }
        }
    }
    cout<<s;
    return 0;
}
