## Gloria-TAF - это легковесное решение для внедрения в проект, где необходимо применить автоматизацию WEB UI тестирования

#### Для запуска необходимо установить следующие аргументы в параметры запуска:

--glue\
StepDefs,BaseTest\
--plugin\
pretty,junit:target/allure-report/output.xml,json:target/cucumber-reporting/output.json,BaseTest.BaseTest\
--browser\
chrome\
--tags\
@Example\
--timeout\
15\
--features\
src/test/resources/Features

glue - относительный путь к пакету, в котором хранятся Степ Дефинишены

plugin - плагины, которые будет использовать кукумбер

browser - браузер, в котором будут гоняться тесты (по умолчанию используется хром)

tags - тэги сценариев для запуска (по умолчанию @Regression)

timeout - максимальное время ожидания элементов (по умолчанию 30)

features - относительный путь к фича файлам

#### ВАЖНО: все параметры для одного пункта в аргументы программы передаются без пробелов, через запятую
Например [--glue StepDefs,BaseTest] - будет работать, а [--glue StepDefs, BaseTest] - нет