<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/inc_taglib.jspf" %>

<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" href="${contextPath}/webjars/ax5ui-grid/1.4.20/dist/ax5grid.css">
<script src="${contextPath}/webjars/ax5core/1.4.20/dist/ax5core.min.js"></script>
<script src="${contextPath}/webjars/ax5ui-grid/1.4.20/dist/ax5grid.js"></script>
<script src="${contextPath}/webjars/ax5ui-formatter/1.4.20/dist/ax5formatter.js"></script>
<script src="${contextPath}/webjars/lodash/4.17.4/lodash.js"></script>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>
				Menu Mng <small></small>
			</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
				<li class="active">Here</li>
			</ol>
		</section>
		<section class="content">
			<div class="row">
				<div class="col-lg-12 col-xs-12">
					<div class="box box box-primary">
			            <div class="box-header with-border">
			              <i class="fa fa-fw fa-gears"></i>
			              <h3 class="box-title">Menu Management</h3>
			            </div>
			            <!-- /.box-header -->
			            <div class="box-body">
			            
			            	<div class="row" style="margin-bottom: 15px;">
			            		<div class="col-lg-6 col-xs-6">
			            			<form name="searchForm">
					            		<div class="input-group">
							                <input type="text" id="filter" name="filter" class="form-control" placeholder="Search..">
						                    <span class="input-group-btn">
						                      <button type="submit" class="btn btn-info btn-flat">Go!</button>
						                    </span>
						            	</div>
					            	</form>
				              	</div>
				              	<div class="text-right col-lg-6 col-xs-6">
				        			<div class="button-warp">
							            <!-- <button type="button" class="btn btn-info" data-page-btn="search"><i class="axi axi-ion-android-search"></i> Inquery </button> -->
							            <button type="button" class="btn btn-info " data-page-btn="save"><i class="axi axi-save"></i> Save</button>
								    </div>
					        	</div>
				        	</div>
				        	
					        <div class="row"  style="margin-bottom: 5px;">
			            		<div class="col-lg-4 col-xs-4">
			            			<fx:common-code groupCd="MENU_GROUP"  defaultValue="SYSTEM" id="menuGrpCd" clazz="form-control"/>
			            		</div>
			            		
			            		<div class="text-right col-lg-8 col-xs-8" >
									<div class="btn-group ">
										<button type="button" class="btn btn-default" data-grid-control="row-add">
											<i class="fa fa-fw fa-plus"></i>
										</button>
										<button type="button" class="btn btn-default" data-grid-control="row-remove">
											<i class="fa fa-fw fa-minus"></i>
										</button>
									</div>
								</div>
			            	</div>
			            	
			            	
			            	<div class="row">
		            			<div class="col-lg-12 col-xs-12" style="margin-bottom: 15px">
						           	<div style="position: relative;height:700px;" id="grid-parent">
										<div data-ax5grid="first-grid" data-ax5grid-config="{}" style="height: 100%;"></div>
									</div>
								</div>
			            	</div>
			            	
			            	<%-- <fx:menu-code menuGrpCd="SYSTEM" var="menus"/>
			            	<ol>
			            		<c:forEach var="list" items="${menus}">
			            			<c:if test="${list.useYn eq 'Y'}">
				            			<li>${list.menuNm} : ${list.progUrl}
					            			<c:if test="${fn:length(list.children) > 0}">
					            				<ul>
					            					<c:forEach var="sub" items="${list.children}">
					            						<c:if test="${sub.useYn eq 'Y'}">
					            							<li>${sub.menuNm} : ${sub.progUrl}</li>
					            						</c:if>
					            					</c:forEach>
					            				</ul>
					            			</c:if>
				            			</li>
				            		</c:if>
			            		</c:forEach>
			            	</ol> --%>
			            	${principal}
			            </div>
			            <!-- /.box-body -->
	            	</div>
				</div>
			</div>
		</section>
	</div>
	<script type="text/javascript">
		var fnObj = {};
		
		fnObj.gridView = {
				initView: function() {
					this.target = new ax5.ui.grid();
					this.target.setConfig({
				    	showLineNumber: true,
				    	showRowSelector: true,
				        frozenColumnIndex: 0,
				        sortable: true,
				        multipleSelect: false,
				    	target: $('[data-ax5grid="first-grid"]'),
				    	header: {
		                    align: "center",
		                    columnHeight: 28
		                },
				    	body: {
				            onClick: function () {
				                this.self.select(this.dindex, {selectedClear: true});
				            },
				            onDataChanged: function () {
			                    if (this.key == 'useYn') {
			                    	this.self.updateChildRows(this.dindex, {useYn: this.item.useYn});
			                    }
			                    else if(this.key == 'sort'){
			                    	var thisSort = this.value;
			                    	var parentId = this.item.parentId;
			                    	var id = this.item.id;
			                    	var sList = _.filter(this.list, function(o) {
			                    		return o.parentId == parentId && o.sort >= thisSort;
			                    	});
			                    	
			                    	var target = this;
			                    	var sortNum = thisSort;
			                    	_.forEach(sList, function(o) {
			                    		if(o.id == id) {
			                    			return;
			                    		}
			                    		target.self.updateRow($.extend({}, o, {sort: ++sortNum}), o.__origin_index__);
		                    		});
			                    }
			                }
				        },
					    tree: {
			                use: true,
			                indentWidth: 20,
			                arrowWidth: 15,
			                iconWidth: 18,
			                icons: {
			                    openedArrow: '<i class="fa fa-fw fa-caret-down"></i>',
			                    collapsedArrow: '<i class="fa fa-fw fa-caret-right"></i>',
			                    groupIcon: '<i class="fa fa-fw fa-folder-open"></i>',
			                    collapsedGroupIcon: '<i class="fa fa-fw fa-folder"></i>',
			                    itemIcon: '<i class="fa fa-fw fa-link"></i>'
			                },
			                columnKeys: {
			                    parentKey: "parentId",
			                    selfKey: "id"
			                }
			            },
				        columns: [            
				            {key: "id", label: "Menu ID", width: 70, align: "center", editor: {type: "text", disabled: function () {
				                return true;
				            }}},
				            {key: "__depth__", label: "Level", width: 70, align: "center", editor: {type: "text", disabled: function () {
				                return true;
				            }}},
				            {key: "menuNm", label: "Menu Name", width: 300, treeControl: true, editor: {type: "text"}},
				            {key: "progUrl", label: "Program URL", width: 500, align: "left", editor: {type: "text"}},
				            {key: "sort", label: "Sort", width: 70, align: "center", formatter: "money", editor: {type: "number"}},
				            {key: "targetBlank", label: "새창 열기", width: 70, sortable: false, align: "center", editor: {
				                type: "checkbox", config: {trueValue: "Y", falseValue: "N"}
				            }},
				            {key: "viewAnony", label: "VIEW ANONY", width: 100, sortable: false, align: "center", editor: {
				                type: "checkbox", config: {trueValue: "Y", falseValue: "N"}
				            }},
				            {key: "useYn", label: "UseYN", width: 70, sortable: false, align: "center", editor: {
				                type: "checkbox", config: {trueValue: "Y", falseValue: "N"}
				            }},
				            {key: "data1", label: "Data1", width: 70, align: "left", editor: {type: "text"}},
				            {key: "data2", label: "Data2", width: 70, align: "left", editor: {type: "text"}}
				        ]
				    });
					return this;
				},
			    setData: function setData(_data) {
			    	
			        this.target.setData({
			        	list: _data,
			        	page: null
			        });
			        
			        return this;
			    },
			    getData: function (_type) {
			        var list = [];
			        var _list = this.target.getList(_type);
		
			        if (_type == "modified" || _type == "deleted") {
			            list = ax5.util.filter(_list, function () {
			                return this.menuNm;
			            });
			        } else {
			            list = _list;
			        }
			        return list;
			    },
			    addRow: function () {
			    	var rowObj = {__created__: true, posUseYn: "N", useYn: "Y", menuGrpCd: $('#menuGrpCd').val()}
			    	try {
			    		var pid = this.target.getList("selected").length == 0 ? null : this.target.getList("selected")[0].id | 0;
			    		var index = "last";
			    		var childList;
				    	if(pid != null) {
				    		rowObj['parentId'] = pid
				    		childList = _.filter(this.target.getList(), { 'parentId': pid });
				    	} else {
				    		childList = _.filter(this.target.getList(), { 'parentId': "top" });
				    	}
				    	
				    	if(this.target.getList("selected").length > 0) {
				    		index = Number(this.target.getList("selected")[0].__index)+1;	
				    	}
			    	} catch(e) {
			    	}

			    	var sort = childList.length <= 0 ? 0 : _.orderBy(childList, ['sort'], ['desc'])[0].sort + 1;
			    	rowObj['sort'] = sort;
			        this.target.addRow(rowObj, index);
			    },
			    updateRow: function() {
			    },
			    delRow: function delRow(_type) {
			        this.target.deleteRow(_type);
			    },
			    align: function align() {
			        this.target.align();
			    },
			    clear: function clear() {
			        this.target.setData({
			            list: [],
			            page: {
			                currentPage: 0,
			                pageSize: 0,
			                totalElements: 0,
			                totalPages: 0
			            }
			        });
			    }
		}
		
		fnObj.searchView = {
		    initView: function () {
		        this.target = $(document["searchForm"]);
		        this.target.attr("onsubmit", "return ACTIONS.PAGE_SEARCH();");
		        this.filter = $("#filter");
		        this.menuGrpCd = $('#menuGrpCd');
		    },
		    getData: function () {
		        return {
		            filter: this.filter.val(),
		            menuGrpCd: this.menuGrpCd.val()
		        }
		    }
		};
		
		
		var API_SERVER = "${contextPath}";//"http://localhost:8080";
		
		var ACTIONS = {
		   		PAGE_SEARCH: function () {
		   	        $.ajax({
		   	        	method: "GET",
		   	            url: API_SERVER + "/system/menuMng/menus",
		   	         	contentType: "application/json;charset=UTF-8",
		   	         	data: $.extend({}, fnObj.searchView.getData()),
		   	            success: function(res) {
		   	            	var a = $.extend({}, res);
		   	            	fnObj.gridView.setData(res);
		   	            }
		   	        });
		   	        return false;
		   	    },
		   	    PAGE_SAVE: function () {
		   	        var saveList = [].concat(fnObj.gridView.getData("modified"));
		   	        saveList = saveList.concat(fnObj.gridView.getData("deleted"));
		   	     	_.map(saveList, function(o) {
			   	     	if(o.parentId == "top") {
		   	        		o.parentId = null;	
	   	        		}
		   	     	});
		   	        $.ajax({
		   	        	method: "PUT",
		   	            url: API_SERVER + "/system/menuMng/menus",
		   	         	contentType: "application/json;charset=UTF-8",
		   	            data: JSON.stringify(saveList),
		   	         	success: function (res) {
		   	         		ACTIONS.PAGE_SEARCH();
		   	            }
		   	        });
		   	    },
		   	    ITEM_ADD: function () {
		   	    	fnObj.gridView.addRow();
		   	    },
		   	    ITEM_DEL: function () {
		   	     	fnObj.gridView.delRow("selected");
		   	    }
		}
		
		function gridInit() {
			fnObj.gridView.initView();
			fnObj.searchView.initView();
			ACTIONS.PAGE_SEARCH();
		}
		    
		    
		$(function() {
			// 그리드 시작
			gridInit();
			
			$('#menuGrpCd').change(function() {
				ACTIONS.PAGE_SEARCH();
			});
			
		    // Grid Row 추가 / 삭제
		    $('[data-grid-control]').click(function () {
		        switch (this.getAttribute("data-grid-control")) {
		            case "row-add":
		                ACTIONS.ITEM_ADD();
		                break;
		            case "row-remove":
		                ACTIONS.ITEM_DEL();
		                break;
		        }
		    });
		    
		    // Grid search 및 저장
		    $('[data-page-btn]').click(function () {
		        switch (this.getAttribute("data-page-btn")) {
		            case "search":
		            	ACTIONS.PAGE_SEARCH();
		                break;
		            case "save":
		            	ACTIONS.PAGE_SAVE();
		                break;
		        }
		    });
		    
		    
		});
	</script>
</body>
</html>