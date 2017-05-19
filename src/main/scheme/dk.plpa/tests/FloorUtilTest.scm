(import
 (rough-draft unit-test)
 (rough-draft console-test-runner))

(include "../FloorUtil.scm")
;Test suite requires (include "../tests/FloorPlanTest1.scm") in FloorUtil


(define-test-suite foo
  (define-test getFactoryFloor-test
    (assert-eqv? (getFactoryFloor) factoryFloor))
  (define-test getTile1-test
    (assert-eqv? (getTile 0 0) 'o))
  (define-test getTile2-test
    (assert-eqv? (getTile 1 0) 'A))
  (define-test getTile3-test
    (assert-eqv? (getTile 1 1) 'i))
  (define-test getTile4-test
    (assert-eqv? (getTile 3 2) 'P))
  (define-test getTileOutOfBound1-test
    (assert-eqv? (getTile -1 -1) '()))
  (define-test getTileOutOfBound2-test
    (assert-eqv? (getTile 60 0) '()))
  (define-test getTileOutOfBound3-test
    (assert-eqv? (getTile 0 60) '()))
  )
 

(run-test-suite foo)