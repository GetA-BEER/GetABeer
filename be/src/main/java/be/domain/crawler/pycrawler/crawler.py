from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from webdriver_manager.chrome import ChromeDriverManager
from crawler_constant import *
from crawler_constant import SEARCH_BOX
import pandas as pd
import openpyxl
from openpyxl import load_workbook
import time

########################################################################################
##                                                                                    ##
##                                Init WebDriver                                      ##
##                                                                                    ##
########################################################################################

chrome_options = Options()

user_agent = 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36'

chrome_options.add_argument('user-agent' + user_agent)
# chrome_options.add_argument('headless')  # 브라우저 실행 안 함
# chrome_options.add_argument('--disable-gpu')  # Linux에서 headless 사용시 필요함
chrome_options.add_argument('--start-fullscreen')  # 최대 크기로 시작
chrome_options.add_argument('--window-size=1920,1080')  # 해상도
chrome_options.add_argument('--disable-extensions')  # 확장 프로그램 사용 안 함
chrome_options.add_argument('--disable-popup-blocking')  # 팝업 비활성화
chrome_options.add_argument('--disable-dev-shm-usage')  # CI가 구현되었거나 Docker를 사용하는 경우
chrome_options.add_argument('--ignore-certificate-errors')  # '안전하지 않은 페이지' 스킵
chrome_options.add_argument('lang=en_US')

driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=chrome_options)


########################################################################################
##                                                                                    ##
##                          Extract And Create Document                               ##
##                                                                                    ##
########################################################################################


def create_info(beer_info):
    '''
    Create And Make a Document
    '''

    df = pd.read_excel("/Users/gnidinger/Desktop/test.xlsx")

    result = list((beer_info.text.split('\n')))

    print(*result, sep='\n')

    info_list = [result[2], result[7], result[8], result[10], result[12]]

    df.loc[df.shape[0]] = info_list

    df.to_excel("/Users/gnidinger/Desktop/test.xlsx", index=False)


########################################################################################
##                                                                                    ##
##                                   Crawling                                         ##
##                                                                                    ##
########################################################################################

# driver.get(START_URL)
#
# time.sleep(5)

for i in range(len(TEST_LIST)):

    driver.get(START_URL)
    time.sleep(5)

    try:
        search_box = driver.find_element(By.XPATH, SEARCH_BOX)
    except NoSuchElementException:
        break
        # driver.get(START_URL)
        # time.sleep(5)
        # search_box = driver.find_element(By.XPATH, SEARCH_BOX)
    else:
        time.sleep(5)
        search_box.send_keys(Keys.COMMAND + 'a')
        time.sleep(1)
        search_box.send_keys(TEST_LIST[i])
        time.sleep(5)
        search_box.send_keys(Keys.TAB, Keys.TAB, Keys.ENTER)
        time.sleep(5)
        WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.XPATH, TARGET_XPATH)))

        beer_info = driver.find_element(By.XPATH, TARGET_XPATH)

        element_length = len(list(beer_info.text.split('\n')))
        if element_length < 13:
            driver.get(START_URL)
            continue
        else:
            create_info(beer_info)

driver.close()
driver.quit()
