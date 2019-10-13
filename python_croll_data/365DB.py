#!/usr/bin/env python
# coding: utf-8

# In[153]:


from bs4 import BeautifulSoup
from urllib.request import urlopen
import pandas as pd


# # 월/ 일 에 따른 링크 주소 자동화

# In[150]:


url_txt  #link 정상 작동 확인완료


# # url 리스트화 (1/1 ~ 12/31)

# In[214]:


def get_url_list():
    month = [1,2,3,4,5,6,7,8,9,10,11,12]
    date_per_month = [31,29,31,30,31,30,31,31,30,31,30,31]
    date = []
    
    date_url = []
    month_url = []
    for i in range(31):
        date.append(i+1)
    
    url_list = []
    for mn in month: #1~12
        for dat in date: #1~31
            mnt = str(mn)
            dt = str(dat)
            if dat > date_per_month[mn - 1]:
                break
            url_txt = "http://contents.history.go.kr/front/th/list.do?newSearch=false&sortDirection=DESC&month=" + mnt + "&day=" + dt
            url_list.append(url_txt)
            date_url.append(dat)
            month_url.append(mn)
            
    return url_list, date_url, month_url


# In[215]:


url_list, date_u_list, month_u_list = get_url_list() #len : 366


# In[218]:


len(month_u_list)


# # 연도 가져오기 함수

# In[157]:


def get_year(url_text):
    html= urlopen(url_text)
    bsObject = BeautifulSoup(html, "html.parser")
    mm = bsObject.body.select(".today_table th")
    date_list = []
    for i in mm:
        date_list.append(i.get_text().strip())
    return date_list


# In[158]:


year_list = get_year(url_txt) #len ; 65


# # 제목 가져오기 함수

# In[159]:


def get_title(url_text):
    html= urlopen(url_text)
    bsObject = BeautifulSoup(html, "html.parser")
    nl = bsObject.body.select(".today_table td")
    title_list = []
    for i in nl:
        tmp = i.get_text().strip()
        tmp = tmp.split('\n')
        title_list.append(tmp[0])
    return title_list


# In[160]:


title_list = get_title(url_txt) #len: 65


# # 링크 가져오기 함수

# In[161]:


def get_link(url_text):
    html= urlopen(url_text)
    bsObject = BeautifulSoup(html, "html.parser")
    link_list = []
    sl = bsObject.body.select(".today_table td div:first-child a:first-child")
    for i in sl:
        link_list.append(i.get('href'))
    return link_list


# In[162]:


link_list = get_link(url_txt) #lend : 65


# # dataframe 형태로 데이터 저장

# In[176]:


url_list[0]
year = []
title = []
link = []
#for link in url_list:
link = url_list[0]
# list type
year = get_year(link)
title = get_title(link)
link = get_link(link)

#df = pd.DataFrame("year": year, "title": title, "link": link)
 


# In[192]:





# In[194]:


df = pd.DataFrame({"year": year, "title": title, "link": link})


# In[195]:


df


# In[197]:


tmm = url_list[:5]
tmm


# In[224]:


year = []
title = []
llll = []

mmmm = []
dddd = []
cntm = 0
cntd = 1
month = [1,2,3,4,5,6,7,8,9,10,11,12]
date_per_month = [31,29,31,30,31,30,31,31,30,31,30,31]
date = []

#for i in range(31):
 #   date.append(i+1)
for link in url_list:
    ty, tt, tl = get_info(link)
    year+=ty
    title+=tt
    llll+=tl
    rr = len(ty)
    for i in range(rr):
        mmmm.append(month[cntm])
        dddd.append(cntd)
    cntd+=1
    if date_per_month[cntm] <cntd:
        cntm+=1
        cntd = 1


# In[228]:


df = pd.DataFrame({"month": mmmm, "date": dddd, "year": year, "title": title, "link": llll})


# In[229]:


df


# In[230]:


df.to_csv("365DB.csv", mode = 'w')


# csv 파일로 제대로 저장이 되는 건 확인완료.
# 1. year, title,link 함 수 한번에 합쳐서 웹 요청 한번으로 줄이기
# 2. dataframe에 월/일 도 같이 계산해서 넣기. 
# 3. 14706 * 5 형태의 데이터 프레임만들면 끝.

# # year title link 함수 합치기

# In[213]:


def get_info(url_text):
    html= urlopen(url_text)
    bsObject = BeautifulSoup(html, "html.parser")
    
    #get year
    mm = bsObject.body.select(".today_table th")
    date_list = []
    for i in mm:
        date_list.append(i.get_text().strip())
        
        
    #get title
    nl = bsObject.body.select(".today_table td")
    title_list = []
    for i in nl:
        tmp = i.get_text().strip()
        tmp = tmp.split('\n')
        title_list.append(tmp[0])
    
    #get link
    link_list = []
    sl = bsObject.body.select(".today_table td div:first-child a:first-child")
    for i in sl:
        link_list.append(i.get('href'))
        
        
    return date_list, title_list, link_list 

