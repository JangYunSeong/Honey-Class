from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.select import Select
import time
import pandas as pd
import os
from professor_function import class_indexing,professor_indexing,login,choose_semester,find_lecture_review,scroll_contents,save_temp_file
load_path="path" # 수강과목 크롤링한 파일 경로
save_path="path" # 크롤링 결과 저장할 파일 경로
result_arr = professor_indexing(load_path)
driver = webdriver.Chrome("C:/Program Files/chromedriver/chromedriver")
id = 'id' # 자기 yes id
password='password' # 자기 yes 비밀번호
driver.get("https://knuin.knu.ac.kr/")

login(driver,id,password) # 로그인
# 이동
find_lecture_review(driver) # 수강평가공개로 이동
# 학기 검색
semester = '2022학년도 1학기' # 학기 선택 ('****학년도 *학기' 형식으로 맞춰줘야함(2021학년도 1학기 ~ 2022학년도 2학기까지 ))
choose_semester(driver,semester=semester)
table = {}
cnt=1
column=['name','professor','total_college','sugang_number','review_number','review_mean','univ_mean','s_deviation','college','major','number'] # DataFrame에 저장할 column
for professor_name in result_arr:
    professor_input = driver.find_element(By.ID,'tabContentMain_contents_tabPgmMNU0010984_body_schCrgePrfssNm').send_keys(professor_name) # 교수님 성함 입력
    driver.implicitly_wait(3)
    search_button = driver.find_element(By.ID,'tabContentMain_contents_tabPgmMNU0010984_body_udcBtns_btnSearch').click() # 검색 버튼
    driver.implicitly_wait(3)
    contents=driver.find_element(By.ID,'tabContentMain_contents_tabPgmMNU0010984_body_grid01_body_tbody') # 강의 리스트 있는 tag로 이동
    driver.implicitly_wait(3)
    reviews = contents.find_elements(By.TAG_NAME,'tr') # tr하나당 강의 하나임
    driver.implicitly_wait(3)
    review_number = driver.find_element(By.ID,'tabContentMain_contents_tabPgmMNU0010984_body_wq_uuid_1504_lblCount') # 강의 총 개수 알아야지 스크롤할지 안할지 결정할 수 있음
    time.sleep(2)
    review_number = int(review_number.text)
    for i in range((review_number//15)+1): # 스크롤하기 전에 가장 많이 보여주는 개수가 15개
        for review in reviews[:15]:
            source=review.find_elements(By.TAG_NAME,'td') # 각 column항목으로 넣어주기 위해 요소 찾음
            result = class_indexing(source) # 배열에 요소 나누어서 넣기
            print(result)
            if result[1] == '':
                pass
            else:
                result.append(result[1]+result[2]) # 학과코드 합쳐주기 CTRL0000 + 002 -> CTRL0000002
                table[result[1]+result[2]]=result[3:] # 딕셔너리에 중복없이 넣어주기
        scroll_contents(driver) # 혹시 더남았으면 스크롤해주기
    cnt=save_temp_file(save_path,cnt,table,column) # 파일에 이때까지 크롤링한 정보 저장하고 이전 파일 지워주기 -> 결과에는 파일 하나만 남겨두기(중간에 끊길수도 있으니깐)