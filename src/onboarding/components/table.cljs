(ns onboarding.components.table
  (:require
    ["@mui/icons-material" :as mui-icons]
    ["@mui/material" :as mui]))


(defn build-header-component
  [column]
  [:> mui/TableCell {:key (:id column)
                     :align (or (:align column) "left")
                     :style (clj->js (:style column))}
   (:label column)])


(defn build-row-cell-component
  [record]
  (fn [column]
    (let [value (get record (:id column))]
      [:> mui/TableCell {:key (:id column)
                         :align (or (:align column) "left")}
       (if-let [format-value (:format column)]
         (format-value value)
         value)])))


(defn build-row-component
  [columns]
  (fn [record]
    (into [:> mui/TableRow {:hover true
                            :tabIndex -1
                            :key (:id record)}]
          (mapv (build-row-cell-component record) columns))))


(defn table-loader
  [rows-count columns-count]
  (mapv (fn [row-index]
          (into [:> mui/TableRow {:key (str row-index)}]
                (mapv (fn [_]
                        [:> mui/TableCell {:component "th" :scope "row"}
                         [:> mui/Skeleton {:animation "wave" :variant "text"}]])
                      (range 1 (inc columns-count)))))
        (range 1 (inc rows-count))))


(defn table
  [{:keys [columns store on-page-change on-records-per-page-change on-page-reload]}]
  (let [records (:records store)
        status (:status store)
        total-count (:total-count store)
        current-page (:current-page store)
        records-per-page (:records-per-page store)
        records-count (count records)
        columns-count (count columns)
        loading? (= status "loading")]
    [:<>
     [:> mui/Paper {:sx (clj->js {:width "100%" :overflow "hidden"})}
      [:> mui/TableContainer {:sx (clj->js {:height "calc(100vh - 116px)"})}
       [:> mui/Table {:stickyHeader true}
        [:> mui/TableHead
         (into [:> mui/TableRow] (mapv build-header-component columns))]

        (into [:> mui/TableBody] (if (or loading? (zero? records))
                                   (table-loader (if (zero? records-count) 10 records-count) columns-count)
                                   (mapv (build-row-component columns) records)))]]

      [:> mui/Box {:display "flex" :justifyContent "space-between" :alignItems "center"}
       [:> mui/IconButton {:onClick on-page-reload
                           :disabled loading?
                           :sx (clj->js {:ml 1})}
        [:> mui-icons/Cached]]
       [:> mui/TablePagination {:showFirstButton true
                                :sx (clj->js {:mr 1})
                                :showLastButton true
                                :rowsPerPageOptions (clj->js [10 25 100])
                                :disabled loading?
                                :component "div"
                                :count total-count
                                :rowsPerPage records-per-page
                                :page current-page
                                :onPageChange on-page-change
                                :onRowsPerPageChange on-records-per-page-change}]]]]))
