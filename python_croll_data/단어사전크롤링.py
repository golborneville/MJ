#https://stdict.korean.go.kr/api/search.do?certkey_no=1045&
#key=7ADE8BF4C0F8A8A9D2CEDB5212D1FA49&type_search=search&q=%EB%B0%B0
import json
import requests
import copy
import xml.etree.ElementTree as et

def makeQuery_1(q):
    search_url = "https://stdict.korean.go.kr/api/search.do?certkey_no=1045&"
    key = "key=7ADE8BF4C0F8A8A9D2CEDB5212D1FA49&type_search=search&"
    url = search_url+key+"q="+q
    return url
def makeQuery_2(q):
    search_url = "http://dic.ezmeta.co.kr/s.php?k="
    url = search_url+q
    return url
def main():
    q = "사"
    xml = requests.get(makeQuery_1(q))
    text = xml.text
    print(xml)
    #print(xml.text)
    root = et.fromstring(text)
    #root = doc.getroot()
    #lst = root.findall('item/item')
    for item in root.iter("item"):
        print ("=========================================================")
        print ("pos = ", item.findtext("pos"))
        for sense in item.iter("sense"):
            print ("mean = ", sense.findtext("definition"))
            print ("link = ", sense.findtext("link"))
            t_t = sense.findtext("type")
            t_t.strip()
            print ("type = "+t_t.strip())
    xml_2 = requests.get(makeQuery_2(q))
    text = xml_2.text
    print(text)
main()
