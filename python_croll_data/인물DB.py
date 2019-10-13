#!/usr/bin/env python
# coding: utf-8

# In[4]:


import json
import requests
import copy
import pandas as pd
import openpyxl
import matplotlib.pyplot as plt
from urllib.request import urlopen
from bs4 import BeautifulSoup
from pprint import pprint as pp
from openpyxl import Workbook
result = []
def readExcel():
    data = pd.read_excel('data_set.xlsx')
    #2~15730
    for i in range(14001,15725):
        id = data.iloc[i]
        id_list = id.tolist()
        url = "http://people.aks.ac.kr/front/dirSer/ppl/pplView.aks?pplI"
        url = url + id_list[0] + "&curSetPos=0&curSPos=0&category=dirSer&isEQ=true&kristalSearchArea=P"
        parsing_data(url)
    
def parsing_data(url):
    #html = urlopen(url)
    html = requests.get(url)
    bsObject = BeautifulSoup(html.text,"html.parser")
    #---상세내용 크롤링--------------------------------------
    my_titles = bsObject.select(
        'div:nth-child(6) > div'
        )
    for title in my_titles:
        beforeSlice = title.text
        _find1 = "[요약정보]"
        _find2 = "[상세내용]"
        _find3 = "[참고문헌]"
        _find4 = "생년"
        
        index_start = beforeSlice.find(_find2)
        index_end = beforeSlice.find(_find3)
        detail_text = beforeSlice[index_start:index_end]
        index_start = beforeSlice.find(_find4)
        index_end = beforeSlice.find(_find2)
        date_text = beforeSlice[index_start:index_end]
        list = [date_text, detail_text]
        result.append(list)
        


# In[5]:


def main():
    readExcel()
    #print(result)
    data = pd.DataFrame(result)
    data.to_excel('data_save.xlsx',encoding='utf-8')


# In[6]:


main()


# In[ ]:




