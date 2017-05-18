(import
 (rough-draft unit-test)
 (rough-draft console-test-runner))

(include "../FloorUtil.scm")


(define-test-suite foo
  (define-test getFactoryFloor-test
    (assert-eqv? (getFactoryFloor) factoryFloor))
  (define-test getTile1-test
    (assert-eqv? (getTile 0 0) 'o))
  (define-test getTile1-test
    (assert-eqv? (getTile 1 0) 'A))
  (define-test getTile1-test
    (assert-eqv? (getTile 1 1) 'i))
  (define-test getTile1-test
    (assert-eqv? (getTile 3 2) 'P))
  )
 

(run-test-suite foo)