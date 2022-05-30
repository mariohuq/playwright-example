# Демонстрация работы с Playwright

на примере теста [презентации](https://github.com/mariohuq/playwright-presentation), использующей [remark](https://github.com/gnab/remark).

## Запуск

```shell
# Run dev server
cd ~
git clone https://github.com/mariohuq/playwright-presentation.git
cd playwright-presentation
python -m http.server &

# Run tests
cd playwright-example
mvn test
```
