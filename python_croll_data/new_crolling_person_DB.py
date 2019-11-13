import json
import requests
import copy
#import pandas as pd
#import openpyxl
#import matplotlib.pyplot as plt
#from urllib.request import urlopen
#from bs4 import BeautifulSoup
#from pprint import pprint as pp
#from openpyxl import Workbook

def getLink():
    for i in range (1,5):
        url = "http://encykorea.aks.ac.kr/Search/Category?category=contenttype&keyword=인물&page=" + str(i)
        html = requests.get(url)
        print(html)
        html_text = html.text
        idx = 0;
        idx2 = 0;
        qq = []
        for j in range(10):
            pars_str = html_text[html_text.find("<li class",idx):html_text.find("</dd>",idx2)]
            idx =html_text.find("<li class",idx) + 1
            idx2 =html_text.find("</dd>",idx2) + 1
            print(pars_str[pars_str.find('/Contents'):pars_str.find('">\r', 16)])
            link = pars_str[pars_str.find('/Contents'):pars_str.find('">\r', 16)]
            qq.append(link)
    

if __name__ == "__main__":
    find_name = 'itemprop="name">'
    find_name2 = '<em>'
    find_tag1 = 'class="bx_r">'
    find_tag2 = 'class="bx_b">'
    find_short = '"articleSection">\r'
    dt = '<dt>'
    dd = '<dd>'
    for i in range (1500,1501):
        url = "http://encykorea.aks.ac.kr/Contents/CategoryNavi?category=contenttype&keyword=%EC%9D%B8%EB%AC%BC&ridx="+str(i)+"&tot=18512"
        html = requests.get(url)
        #print(html)
        html_text = html.text
        idx = html_text.find('itemprop="name"');
        pars_str = html_text[idx:html_text.find("</div>",idx)]
        #print(pars_str)
        name = pars_str[pars_str.find(find_name)+len(find_name)
                        :pars_str.find(find_name2)] + pars_str[pars_str.find(find_name2)+len(find_name2):pars_str.find('</em>')]
        tag1 = pars_str[pars_str.find(find_tag1)+len(find_tag1):pars_str.find('</em>',pars_str.find(find_tag1))]
        tag2 = pars_str[pars_str.find(find_tag2)+len(find_tag2):pars_str.find('</em>',pars_str.find(find_tag2))]
        short = pars_str[pars_str.find(find_short)+len(find_short)+16:pars_str.find('\r</p>',pars_str.find(find_short))-len('\r</p>')-16]
        print(name)
        print(tag1)
        print(tag2)
        print(short)
        idx = html_text.find('<dl class="left">');
        pars_str = html_text[idx:html_text.find("</div>",idx)]
        print(pars_str)
        #dx = pars_str.find(dt)
        #dx2 = pars_str.find(dd)
        dx = 0
        dx2 = 0
        s_s = []
        for aa in range(pars_str.count(dt)):
            
            tmp1 = pars_str[pars_str.find(dt, dx)+len(dt):pars_str.find('</dt>')]
            dx = pars_str.find('</dt>',dx)
            tmp2 = pars_str[pars_str.find(dd,dx2)+len(dd):pars_str.find('</dd>')]
            dx2 = pars_str.find('</dd>',dx2)
            tmp = tmp1+" : "+tmp2
            print(tmp)
            s_s.append(tmp)
