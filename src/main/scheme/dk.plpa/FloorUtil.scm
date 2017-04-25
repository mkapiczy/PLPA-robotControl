(include "FloorPlan.scm")

(define get-tile
    (lambda (x y)
        (vector-ref (vector-ref floorr y) x)
    )
)
