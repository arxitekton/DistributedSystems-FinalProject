<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div>
    <sec:authorize access="isAuthenticated()">
        <p class="nav navbar-nav navbar-right navbar-text sign-in-text">
			<spring:message code="label.navigation.signed.in.as.text"/>
			<sec:authentication property="principal.username"/>
		</p>
		</sec:authorize>
</div>


<form class="form-wrapper">
	<input type="text" id="search" placeholder="EmailOrLastnameOrFirstname:" required>
	<input type="submit" value="go" id="submit">
</form>

<div id="UserTableContainer"></div>

<script type="text/javascript">
			

			var QueryStringToHash = function QueryStringToHash  (query) {
			  var query_string = {};
			  var vars = query.split("&");
			  for (var i=0;i<vars.length;i++) {
				var pair = vars[i].split("=");
				pair[0] = decodeURIComponent(pair[0]);
				pair[1] = decodeURIComponent(pair[1]);
					// If first entry with this name
				if (typeof query_string[pair[0]] === "undefined") {
				  query_string[pair[0]] = pair[1];
					// If second entry with this name
				} else if (typeof query_string[pair[0]] === "string") {
				  var arr = [ query_string[pair[0]], pair[1] ];
				  query_string[pair[0]] = arr;
					// If third or later entry with this name
				} else {
				  query_string[pair[0]].push(pair[1]);
				}
			  }
			  return query_string;
			};

			function QueryStringToJSON(str) {
				var pairs = str.split('&');
				var result = {};
				pairs.forEach(function(pair) {
					pair = pair.split('=');
					var name = pair[0]
					var value = pair[1]
					if( name.length )
						if (result[name] !== undefined) {
							if (!result[name].push) {
								result[name] = [result[name]];
							}
						result[name].push(value || '');
						} else {
							result[name] = value || '';
						}
				});
				return( result );
			}

      
		function cleanProtection(somelink) {
			return somelink.replace(/ *\{[^}]*\} */g, "");
		}


            var cachedManufactureOptions = null;
            var cachedModelsOptions = [];
            var cachedSubmodelsOptions = [];

            var cachedOwnershipOptions = null;
            var cachedGearboxOptions = null;
            var cachedEngineOptions = null;
            var cachedAreaOptions = null;
            var cachedYearsOptions = [];

			var cachedModelOptions = null;
            var cachedManufactureOptions = null;

			$(document).ready(function () {
				

				$('#UserTableContainer').jtable({
					title: 'User List',
					paging: true,
					pageSize: 10,
					sorting: true,
					multiSorting: true,
					
					selecting: true, //Enable selecting
					multiselect: true, //Allow multiple selecting
					selectingCheckboxes: true, //Show checkboxes on first column
					//selectOnRowClick: false, //Enable this to only select using checkboxes

					toolbar: {
						hoverAnimation: true, //Enable/disable small animation on mouse hover to a toolbar item.
						hoverAnimationDuration: 60, //Duration of the hover animation.
						hoverAnimationEasing: undefined, //Easing of the hover animation. Uses jQuery's default animation ('swing') if set to undefined.
						//Array of your custom toolbar items.
						 items: [{                          
							text: "<span  color='red'><i class='btnIcon btnClear icon-remove-sign'></i></span> Delete all selected",
							click: function () {
								var $selectedRows = $('#UserTableContainer').jtable('selectedRows');
								$('#UserTableContainer').jtable('deleteRows', $selectedRows);
							}
						}]
                    },

					
					defaultSorting: 'id DESC',
//					columnResizable: true,
					ajaxSettings: {
						type: 'GET',
					},

					actions: {
						listAction: function (postData, jtParams) {
							console.log("Loading from custom function...");
							return $.Deferred(function ($dfd) {
                                var sortingParams = jtParams.jtSorting.split(' ');
                                console.log(sortingParams);
								$.ajax({
									url: '/api/users/search/findDistinctUserByEmailContainingOrLastnameContainingOrFirstnameContaining?email=%25'  +  $('#search').val() + '%25&lastname=%25'  +  $('#search').val() + '%25&firstname=%25'  +  $('#search').val() + '%25&projection=privateUser&page=' + jtParams.jtStartIndex/jtParams.jtPageSize + '&size=' + jtParams.jtPageSize + '&sort=' + sortingParams[0] + "," + sortingParams[1].toLowerCase(),
									type: 'GET',
									dataType: 'json',
									data: postData,
									success: function (res) {
										
										console.log("res._embedded.page.totalElements: " + res.page.totalElements);
										                                        
										data={
										 "Result": "OK",
										 "Records": res._embedded.user,
										 "TotalRecordCount": res.page.totalElements, 
										};
										
										$dfd.resolve(data);
									},
									error: function () {
										$dfd.reject();
									}
								});
							});
						},
						deleteAction: function (postData) {
							console.log("deleting from custom function...");
							return $.Deferred(function ($dfd) {
								$.ajax({
									url: '/api/users/' + postData.id,
									type: 'DELETE',
									dataType: 'json',
									data: postData,
									success: function (res) {

										data={
										 "Result":"OK",
										 "Records": res
										};

										$dfd.resolve(data);
									},
									error: function () {
										$dfd.reject();
									}
								});
							});
						},
						createAction: function (postData) {
							console.log("creating from custom function...");
							return $.Deferred(function ($dfd) {
								console.log(QueryStringToJSON(postData));
								console.log(QueryStringToHash(postData));
								
								data = QueryStringToHash(postData)
								data.photos = newUserPhoto;
								delete data.user;
								
								console.log("postDATA:");
								console.log(postData);
								
								$.ajax({
									url: '/api/users',
									type: 'POST',
									data: JSON.stringify(data),
									contentType: "application/json",
									dataType: 'json',
									success: function (res) {

										data={
										 "Result":"OK",
										 "Record": res
										};

										$dfd.resolve(data);
										newUserPhoto = new Array();
										$('#driversTable').jtable('reload');
									},
									error: function () {
										$dfd.reject();
									}
								});
							});
						},
						updateAction: function(postData) {
							console.log("updating from custom function...");
							return $.Deferred(function ($dfd) {
								$.ajax({
									url: '/api/users/' + QueryStringToJSON(postData).id,
									type: 'PATCH',
									contentType: "application/json",
									dataType: 'json',
									data: JSON.stringify(QueryStringToJSON(postData)),
									success: function (res) {

										data={
										 "Result":"OK",
										 "Records": res
										};

										$dfd.resolve(data);
										//$('#UserTableContainer').jtable('load');
									},
									error: function () {
										$dfd.reject();
									}
								});
							});
						}
					},
/*					
					recordsLoaded: function(event, data) {
						$('.gallery').justifiedGallery('norewind');
					},
*/					
					recordAdded: function (event, data) {
						if (data.record) {
							$('#UserTableContainer').jtable('reload');
						}
					},
					
					recordUpdated: function (event, data) {
						if (data.record) {
							$('#UserTableContainer').jtable('reload');
						}
					},
					
					deleteConfirmation: function(data) {
						data.deleteConfirmMessage = 'Are you sure to delete user ' + data.record.id + '?';
					},

					dialogShowEffect: 'fade', // 'blind', 'bounce', 'clip', 'drop', 'explode', 'fold', 'highlight', 'puff', 'pulsate', 'scale', 'shake', 'size', 'slide'



					fields: {

						email: {
							title: 'email',
						},

						id: {
							key: true,
							create: false,
							edit: true,
							list: false,

							title: 'id',
							input: function (data) {
								if (data.record.id) {
									return '<input type="text" readonly class="jtable-input-readonly" name="id" value="' + data.record.id + '"/>';
								}
							},
						},

                        creationTime: {
							create: false,
							edit: false,
							list: true,

							title: 'creation',
                            display: function (data) {
								if (data.record.creationTime == null) {
									return data.record.creationTime;
								}
                                
                                return "<span style='color:gray; font-size: 10px;'>" + data.record.creationTime.split('T').join('\n') + "</span>";
                            }
						},
						
						firstname: {
							title: 'firstname',
						},
						
						lastname: {
							title: 'lastname',
						},
						
						role: {
							title: 'role',
						},
						
						phone: {
							title: 'phone',
						},
						
						usePhoneCall: {
							title: 'usePhoneCall',
							type: 'checkbox',
							values: { 'false': 'not', 'true': 'yes' },
						},
												
						useSMS: {
							title: 'useSMS',
							type: 'checkbox',
							values: { 'false': 'not', 'true': 'yes' },
						},

					}
				});


				$('.jtable').wrap('<div class="jtable-main-container scroll-content" />');

				$('#submit').click(function (e) {
					e.preventDefault();
					$('#UserTableContainer').jtable('load');
				});

				$('#submit').click();

				$("#search").keyup(function(e) {

					if (!this.value) {
						e.preventDefault();
						$('#UserTableContainer').jtable('load');
					}

				});
				
				// Delete selected students
				$('#DeleteAllButton').button().click(function () {
					var $selectedRows = $('#UserTableContainer').jtable('selectedRows');
					$('#UserTableContainer').jtable('deleteRows', $selectedRows);
				});

			});
			

</script>
