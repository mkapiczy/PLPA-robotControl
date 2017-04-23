(include "FloorPlan.scm")

(define get-item
    (lambda (x y)
        (vector-ref (vector-ref floorr y) x)
    )
)
