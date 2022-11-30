from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.select import Select
import time
import pandas as pd
import os

def class_indexing(content:list)->list:
    result=[]
    for i in content:
        result.append(i.text)
    return result

def professor_indexing(file_path:str)->list: # 크롤링한 수강과목에서 교수님 이름 모두 담기
    subject = pd.read_csv(file_path,sep='\t')
    professor_arr=list(subject['professor'].unique())
    result=list()
    for professor in professor_arr:
        if type(professor) == str: # 교수가 없는 과목들은 제외
            professors=professor.split(',')
            for content in professors:
                result.append(content)
    return result

def login(driver:webdriver.Chrome,id:str,password:str)->None:
    driver.implicitly_wait(2)
    driver.find_element(By.ID,'idpw_id').send_keys(id)
    driver.find_element(By.ID,'idpw_pw').send_keys(password)
    driver.find_element(By.ID,'btn-login').click()
    driver.implicitly_wait(2)
    time.sleep(1)

def find_lecture_review(driver:webdriver.Chrome)->None:
    driver.find_element(By.ID,'mainSnb_title_level2_acc_MNU0010929').click()
    time.sleep(1)
    driver.find_element(By.ID,'mainSnb_level3_snbList_button_MNU0010980').click()
    time.sleep(1)
    driver.find_element(By.ID,'mainSnb_level4_snbList_a_MNU0010984').click()
    time.sleep(1)

def choose_semester(driver:webdriver.Chrome,semester:str)->None:
    season = Select(driver.find_element(By.ID,'tabContentMain_contents_tabPgmMNU0010984_body_schEstblYearSmstrSctcd'))
    season.select_by_visible_text(semester)
    driver.implicitly_wait(2)

def scroll_contents(driver:webdriver.Chrome,scrolls=300)->None:
    scroll_command = "arguments[0].scrollBy(0, {scroll})".format(scroll=scrolls)
    scroll_y = driver.find_element(By.ID,'tabContentMain_contents_tabPgmMNU0010984_body_grid01_scrollY_div')
    driver.implicitly_wait(2)
    driver.execute_script(scroll_command, scroll_y)
    driver.implicitly_wait(2)
    time.sleep(2)

def save_temp_file(save_path:str,name:int,table:dict,column:list)->int:
    frame_result = pd.DataFrame(table.values(),columns=column)
    frame_result.to_csv(save_path+str(name)+".tsv",index=False,sep='\t')
    if name>1:
        os.remove(save_path+str(name-1)+".tsv")
    name+=1
    return name