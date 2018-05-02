# Matrix calculator [![Build Status](https://travis-ci.org/ezkijez/matrix-calc.svg?branch=master)](https://travis-ci.org/ezkijez/matrix-calc) [![codecov](https://codecov.io/gh/ezkijez/matrix-calc/branch/master/graph/badge.svg)](https://codecov.io/gh/ezkijez/matrix-calc)
ELTE-IK Project Tools

[Matrix calculator on Heroku](https://matrix-calculator.herokuapp.com/)

Az alkalmazás a mátrixokkal való számolást segíti.
Segítségével könnyen számolható mátrix inverze és determinánsa, mátrixok összege, különbsége és szorzata, valamint mátrix szorzata egész számmal.
A felhasználónak a felületen lehetősége van kiválasztani, milyen műveletet szeretne végrehajtani, majd megadhatja a mátrixot, amellyel számolni szeretne.
Az alkalmazás használata egyszerű és gyors mindenki számára.

### Alkalmazás fejlesztéséhez használt eszközök
- Projektautomatizációs eszköz: Gradle
- Dokumentáció: Javadoc
- Kódolási stílus: Checkstyle használata
- Tesztelés: JUnit Framework
- Teszt lefedettség ellenőrzése: Jacoco és [codecov.io](https://codecov.io/)
- CI: Travis
- CD: Heroku

### Használat

##### Alkalmazás futtatása:

```
./gradlew bootRun
```

##### Tesztek futtatása:

```
./gradlew check
```

Ennek eredménye a

- Jacoco teszt lefedettségi report: /build/reports/jacoco/test/html/index.html
- Checkstyle report: /build/reports/checkstyle/main.html

##### Dokumentáció generálása:

```
./gradlew javadoc
```

- javadoc elérése: /build/docs/javadoc/index.html