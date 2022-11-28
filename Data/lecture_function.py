from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.select import Select
import time
import pandas as pd

def class_indexing(content:list)->list:
    result=[]
    for i in content:
        result.append(i.text)
    return result

def choose_year(driver:webdriver.Chrome,year:int)->None:
    driver.find_element(By.ID,'schEstblYear___input').clear()
    driver.implicitly_wait(2)
    driver.find_element(By.ID,'schEstblYear___input').send_keys(str(year))

def choose_semester(driver:webdriver.Chrome,semester:str)->None:
    season = Select(driver.find_element(By.XPATH,'/html/body/div[1]/div/div/div[2]/div[1]/table/tbody/tr[1]/td[2]/select'))
    driver.implicitly_wait(2)
    season.select_by_visible_text(semester)
    driver.implicitly_wait(2)

def choose_classcode(driver:webdriver.Chrome,code:str)->None:
    search_option = Select(driver.find_element(By.XPATH,'/html/body/div[1]/div/div/div[2]/div[1]/table/tbody/tr[1]/td[4]/select[1]'))
    driver.implicitly_wait(2)
    search_option.select_by_visible_text(code)
    driver.implicitly_wait(2)

def make_class_code(tag:int)->str:
    tag_str = ""
    for i in range(4-len(str(tag))):
        tag_str+='0'
    tag_str+=str(tag)
    return tag_str

def lecture_list_scroll(driver:webdriver.Chrome,scroll:int)->None:
    scroll_command = "arguments[0].scrollBy(0, {scroll})".format(scroll=scroll)
    scroll_y = driver.find_element(By.ID,'grid01_scrollY_div')
    driver.implicitly_wait(2)
    driver.execute_script(scroll_command, scroll_y)
    driver.implicitly_wait(2)
    time.sleep(2)

def make_lecture_table(driver:webdriver.Chrome,length:int,scroll:int)->dict:
    table = {}
    for i in range((length//10)+2):
        lecture_table = driver.find_element(By.ID,"grid01_body_tbody")
        driver.implicitly_wait(2)
        lecture_array = lecture_table.find_elements(By.TAG_NAME,'tr')
        driver.implicitly_wait(2)
        for content in lecture_array[:18]:
            source=content.find_elements(By.TAG_NAME,'td')
            result = class_indexing(source)
            print(result)
            if result[7] == '':
                pass
            else:
                table[result[7]]=result[1:]
        lecture_list_scroll(driver,scroll)
    return table

def save_table(table:dict)->pd.DataFrame:
    column=['year','semester','grade','type','college','major','number',
            'name','credit','lecture_credit','prac_credit','professor',
            'school_time','real_time','building','room','total','sugang','sugangpack','pack_check','information']
    frame_result = pd.DataFrame(table.values(),columns=column)
    return frame_result