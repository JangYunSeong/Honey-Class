from selenium import webdriver
from selenium.webdriver.common.by import By
import pandas as pd
import time
import os
from lecture_function import choose_classcode,choose_semester,make_class_code,make_lecture_table,save_table,choose_year

def make_file_path(year:int,semester:int)->str:
    result = str(year)+'-'+str(semester)
    os.mkdir(result)
    result+='/'
    return result

def concat_files(path):
    files = os.listdir(path)
    total_data = pd.read_csv(path+files[0],sep='\t')
    for file in files[1:]:
        temp_data=pd.read_csv(path+file,sep='\t')
        total_data=pd.concat([total_data,temp_data]).reset_index(drop=True)
    total_data.to_csv(path[:-1]+'.tsv',index=False,sep='\t')

def lecture_crawl(driver:webdriver.Chrome,year:int,semester:str)->None:
    file_path = make_file_path(year,semester)
    choose_year(year) # 년도 입력
    choose_semester(driver,semester) # 학기 입력
    choose_classcode(driver,'교과목코드')
    time.sleep(1)
    for tag in range(0,1000): # 과목코드가 COMP+0XXX-001 이런식으로 되어있음
        tag_str = make_class_code(tag)
        lecture_code = driver.find_element(By.ID,'schCodeContents').send_keys(tag_str) #교과목코드에 0xxx식으로 넣음(겹치는거 없게)
        driver.implicitly_wait(2)
        search_button=driver.find_element(By.ID,'btnSearch').click() # 검색버튼
        driver.implicitly_wait(2)
        lecture_number=driver.find_element(By.XPATH,'/html/body/div[1]/div/div/div[2]/div[2]/div/div/div[1]/div/div[1]/div[2]/label[1]') # 검색결과 개수
        time.sleep(2)
        length = int(lecture_number.text) # 검색한 전체 개수(for문 돌리기 위해서 사용)
        table =make_lecture_table(driver,length,scroll=190) # 찾은 항목들을 table에 넣고 scroll내림(scroll이 마지막까지 갈때까지)
        frame_result=save_table(table) # table을 dataframe으로 저장
        frame_result.to_csv(file_path+tag_str+".tsv",sep='\t')
        driver.find_element(By.ID,'schCodeContents').clear() # input에 넣은 교과목코드 지워주기(다음꺼 실행때 text 겹치면 안되니깐)


driver = webdriver.Chrome("C:/Program Files/chromedriver/chromedriver")
driver.get('https://sy.knu.ac.kr')
driver.maximize_window()
year_list = [i for i in range(2020,2023)]
semester_list = ['1학기','계절학기(하계)','2학기','계절학기(동계)']
for year in year_list:
    for semester in semester_list:
        lecture_crawl(driver,year,semester) # 크롤링
        concat_files(str(year)+'-'+semester) # 코드별로 나눈 파일 다 합쳐주기