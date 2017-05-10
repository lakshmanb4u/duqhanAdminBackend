/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var isSizee = true;
var isColor = true;
var isFirst = true;

$(document).ready(function () {

    $(".dropdown-menu").on("click", "li a", function () {
        $(this).parents(".dropdown").find('.btn').html($(this).text() + ' <span class="caret"></span>');
        $(this).parents(".dropdown").find('.btn').val($(this).data('value'));
    });
    $(".dropdown-menu").on("click", "li.category", function () {
        $("#categoryId").val($(this).prop('id'));
        $("#categoryId").val($(this).attr('id'));
        getSpecifications($("#categoryId").val());
    });
    $(".dropdown-menu").on("click", "li.vendor", function () {
        $("#vendorId").val($(this).prop('id'));
        $("#vendorId").val($(this).attr('id'));
    });
    $(".dropdown-menu").on('click', 'li.sizee', function () {
        $("#sizeId").val($(this).closest('li').prop('id'));
        $("#sizeId").val($(this).closest('li').attr('id'));
        $("#sizetextId").val($(this).text());
    });
    $(".dropdown-menu").on("click", "li.color", function () {
        $("#colorId").val($(this).prop('id'));
        $("#colorId").val($(this).attr('id'));
        $("#colortextId").val($(this).text());
    });
    $(".dropdown-menu").on("click", "li.sizegroup", function () {
        $("#sizegroupId").val($(this).prop('id'));
        $("#sizegroupId").val($(this).attr('id'));
    });
    $(".dropdown-menu").on("click", "li.parentcategory", function () {
        $("#parentcategoryId").val($(this).prop('id'));
        $("#parentcategoryId").val($(this).attr('id'));
    });
//    $(".dropdown-menu").on("click", "li.categoryspec", function () {
//        $("#categoryspecId").val($(this).prop('id'));
//        $("#categoryspecId").val($(this).attr('id'));
//    });
    $('#discountid').on('keyup blur change', function (e) {
        if (parseFloat($('#discountid').val()) > parseFloat($('#priceid').val())) {
            $('#discountid').val($('#priceid').val());
        }
    });
    $('#priceid').on('keyup blur change', function (e) {
        $('#discountid').val($('#priceid').val());
    });
    $("#fitures-div").on("click", ".fitures-row .spcvalueid", function () {
        $("#specId").val($(this).prop('id'));
        $("#specId").val($(this).attr('id'));
    });
     
//==========================Image Validate========================//

    function displayPreview(files) {
        var reader = new FileReader();
        var img = new Image();
        reader.onload = function (e) {
            img.src = e.target.result;
            fileSize = Math.round(files.size / 1024);
//                alert("File size is " + fileSize + " kb");
//                swal('Image!', 'File size is + fileSize +  kb', 'success');
            img.onload = function () {
                var height = this.height;
                var width = this.width;
                if (height > 512 || width > 512) {
                    swal('Error!', 'Your image is ' + this.width + ' X ' + this.height + '. max image is 512 X 512.', 'warning');
                    $("#fileId").val("");
                    return false;
                }
                $('#preview').append('<img src="' + e.target.result + '"/>');
            };
        };
        reader.readAsDataURL(files);
    }
    $("#fileId").change(function () {
        var fileId = this.files[0];
//            displayPreview(fileId);
    });
//===========================image upload==========================//
    $("#image-form").submit(function (e) {
        var that = $(this);
        e.preventDefault();
        var data = new FormData($(this)[0]);
        $.ajax({
            url: that.attr("action"),
            data: data,
            dataType: 'text',
            processData: false,
            contentType: false,
            type: 'POST',
            success: function (data) {
                var jdata = JSON.parse(data);
                if (jdata.status !== "failure") {
                    if ($("#ImgFront").val() === "") {
                        $("#ImgFront").val(jdata.status);
                        $("#ImgFront").prop('disabled', true);
                        $("#fileId").val("");
                    } else {
                        addImageTable(jdata.status);
                    }
                } else {
                    alert("Error: Image not upload..Try again");
                }
            }
        });
    });
//===========================form submit===========================//
    $("#addProductId").submit(function (e) {
        var that = $(this);
        e.preventDefault();
        var jsondata = {};
        jsondata.name = $('#nameid').val();
        jsondata.imgurl = $('#ImgFront').val();
        jsondata.categoryId = $('#categoryId').val();
        jsondata.vendorId = $('#vendorId').val();
        jsondata.description = $('#descriptionid').val();
        jsondata.shippingTime = $('#shippingtimeid').val();
        jsondata.shippingRate = $('#shippingcostid').val();
        jsondata.externalLink = $('#externallinkid').val();
        jsondata.specifications = findSpecifications();
        var imageDtos = [];
        $('.subimg').each(function () {
            var imageDto = {};
            imageDto.imgUrl = $(this).find('td').eq(0).text();
            imageDtos.push(imageDto);
        });
        jsondata.imageDtos = imageDtos;
        var sizeColorMaps = [];
        $('.maptr').each(function (index) {
            var sizeColorMap = {};
            sizeColorMap.colorId = $(this).find('.colorkey').val();
            sizeColorMap.sizeId = $(this).find('.sizekey').val();
            sizeColorMap.orginalPrice = $(this).find('td').eq(2).text();
            sizeColorMap.salesPrice = $(this).find('td').eq(3).text();
            sizeColorMap.count = $(this).find('td').eq(4).text();
            sizeColorMap.productLength = $(this).find('td').eq(5).text();
            sizeColorMap.productWidth = $(this).find('td').eq(6).text();
            sizeColorMap.productHeight = $(this).find('td').eq(7).text();
            sizeColorMap.productWeight = $(this).find('td').eq(8).text();
            sizeColorMaps.push(sizeColorMap);
            //                    console.log(index + ": " + $(this).text());
        });
        jsondata.sizeColorMaps = sizeColorMaps;
        console.log("ttttttttttttttttttt" + JSON.stringify(jsondata));
        if (sizeColorMaps.length > 0 && $('#categoryId').val() !== null && $('#categoryId').val() !== '' && $('#vendorId').val() !== null && $('#vendorId').val() !== '' && sizeColorMaps.length > 0) {
            $.ajax({
                type: "POST",
                data: JSON.stringify(jsondata),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                url: that.attr("action"),
                success: function (data) {
                    location.reload(true);
                    console.log(data);
                },
                complete: function () {
                    return false; // required to block normal submit ajax used
                }
            });
        } else {
            alert("REQUIRD : Category, Vendor and product.");
        }
    });
//========================add map table===========================

    $("#add").click(function () {
        var addPermit = true;
        if (isFirst) {
            if ($("#sizeId").val() === "") {
                isSizee = false;
                $("#szbid").prop('disabled', true);
            }
            if ($("#colorId").val() === "") {
                isColor = false;
                $("#clbid").prop('disabled', true);
            }
            if ($("#sizeId").val() === "" && $("#colorId").val() === "") {
                $("#add").prop('disabled', true);
            }
            isFirst = false;
        } else {
            if ($("#sizeId").val() === "" && isSizee) {
                alert("Please enter size");
                addPermit = false;
            }
            if ($("#colorId").val() === "" && isColor) {
                alert("Please enter color");
                addPermit = false;
            }
        }

        if (addPermit) {
            if ($("#priceid").val() !== "" && $("#discountid").val() !== "" && $("#quntid").val() !== "") {
                var itemCount = 0;
                var objs = [];
                var html = "";
                var obj = {
                    "ROW_ID": itemCount,
                    "ITEM_Size": $("#sizeId").val(),
                    "ITEM_SizeText": $("#sizetextId").val(),
                    "ITEM_Color": $("#colorId").val(),
                    "ITEM_ColorText": $("#colortextId").val(),
                    "ITEM_Price": $("#priceid").val(),
                    "ITEM_Discount": $("#discountid").val(),
                    "ITEM_Quantity": $("#quntid").val(),
                    "PRODUCT_LENGTH": $("#Productlen").val(),
                    "PRODUCT_WIDTH": $("#Productwidth").val(),
                    "PRODUCT_HIGHT": $("#ProductHig").val(),
                    "PRODUCT_WIGHT": $("#Productwight").val()
                };
                objs.push(obj);
                itemCount++;
                html = "<tr id='tr" + itemCount + "' class='maptr'><td>" + obj['ITEM_SizeText'] + "<input type='hidden' class='sizekey' value=" + obj['ITEM_Size'] + "></td> <td>" + obj['ITEM_ColorText'] + "<input type='hidden' class='colorkey' value=" + obj['ITEM_Color'] + "> </td> <td>" + obj['ITEM_Price'] + " </td> <td>" + obj['ITEM_Discount'] + " </td> <td>" + obj['ITEM_Quantity'] + " </td> <td>" + obj['PRODUCT_LENGTH'] + " </td> <td>" + obj['PRODUCT_WIDTH'] + " </td> <td>" + obj['PRODUCT_HIGHT'] + " </td> <td>" + obj['PRODUCT_WIGHT'] + " </td> </tr>";
                $("#bill_table").append(html);
                $("#" + itemCount).click(function () {
                    var buttonId = $(this).attr("id");
                    $("#tr" + buttonId).remove();
                });
                $("#sizeId").val("");
                $("#colorId").val("");
                $("#priceid").val("0");
                $("#discountid").val("0");
                $("#quntid").val("1");
                $("#szbid").html("Size");
                $("#clbid").html("Color");
                $("#Productlen").val("0");
                $("#Productwidth").val("0");
                $("#ProductHig").val("0");
                $("#Productwight").val("0");
            } else {
                alert("REQUIR: Original Price, Sale Price, Quantity.");
            }
        }
    });
//========================add more img===========================
    $("#addImg").click(function () {
        if ($("#imgid").val() !== "") {
            addImageTable($("#imgid").val());
        }
    });
    //*********************Get Color ******************

    $(".get-color").click(function () {
        $.ajax({
            type: "GET",
            dataType: "json",
            url: "/admin/get-color",
            success: function (data) {
                console.log(data.vendorDtos);
                $(".get-color-list").html("");
                var html = "";
                var colorDtos = data.colorDtos;
                for (var i = 0; i < colorDtos.length; i++) {
                    var color = colorDtos[i];
                    html = html + "<li class=\"color\" id=\"" + color.colorId + "\"><a href=\"#\">" + color.colorText + "</a></li>";
                }
                $(".get-color-list").append(html);
                console.log(html);
            },
            complete: function () {
                return false;
            }
        });
    });
    //**************** Add Color *********************
    $("#addColorSubmit").click(function () {
        var jsondata = {};
        var color = $('#addcolorId').val();
        jsondata.name = $('#addcolorId').val();
        if (color !== '' && color !== null) {
            $.ajax({
                type: "POST",
                data: JSON.stringify(jsondata),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                url: "/admin/save-color",
                success: function (data) {
                },
                complete: function () {
                }
            });
        }
    });
    //*********************Get size group ******************

    $(".get-sizegroup").click(function () {
        $.ajax({
            type: "GET",
            dataType: "json",
            url: "/admin/get-sizegroup",
            success: function (data) {
//                console.log(data.vendorDtos);
                $(".get-sizegroup-list").html("");
                var html = "";
                var sizeGroupDtos = data.sizeGroupDtos;
                for (var i = 0; i < sizeGroupDtos.length; i++) {
                    var sizeGroup = sizeGroupDtos[i];
                    html = html + "<li class=\"sizegroup\" id=\"" + sizeGroup.sizeGroupId + "\"><a href=\"#\">" + sizeGroup.sizeText + "</a></li>";
                }
                $(".get-sizegroup-list").append(html);
                console.log(html);
            },
            complete: function () {
                return false;
            }
        });
    });
    // ****************** Add size group ************************
    $("#addSizeGroupSubmit").click(function () {
        var jsondata = {};
        var sizeGroop = $('#AddSizeGroupId').val();
        jsondata.name = $('#AddSizeGroupId').val();
        if (sizeGroop !== '' && sizeGroop !== null) {
            $.ajax({
                type: "POST",
                data: JSON.stringify(jsondata),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                url: "/admin/save-sizegroup",
                success: function (data) {
                },
                complete: function () {
                }
            });
        }
    });
    //*********************Get size ******************

    $(".get-size").click(function () {
        $.ajax({
            type: "GET",
            dataType: "json",
            url: "/admin/get-size",
            success: function (data) {
//                console.log(data.vendorDtos);
                $(".get-size-list").html("");
                var html = "";
                var sizeDtos = data.sizeDtos;
                for (var i = 0; i < sizeDtos.length; i++) {
                    var sizee = sizeDtos[i];
                    html = html + "<li class=\"sizee\" id=\"" + sizee.sizeId + "\"><a>" + sizee.sizeText + "</a></li>";
                }
                $(".get-size-list").append(html);
                console.log(html);
            },
            complete: function () {
                return false;
            }
        });
    });

//    $(".get-size-list").on("click", function(){
//        console.log('CLICKED');
//        console.log(this);
//    });
    //******************** Save size *********************
    $("#addSizeSubmit").click(function () {
        var jsondata = {};
        jsondata.sizeGroupId = $('#sizegroupId').val();
        jsondata.sizeText = $('#size-name').val();
        if ($('#sizegroupId').val() !== null && $('#sizegroupId').val() !== "" && $('#size-name').val() !== null && $('#size-name').val() !== "") {
            $.ajax({
                type: "POST",
                data: JSON.stringify(jsondata),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                url: "/admin/save-size",
                success: function (data) {
                },
                complete: function () {
                }
            });
        }
    });

    //*********************Get Category Parent ******************

    $(".get-category-parent").click(function () {
        $.ajax({
            type: "GET",
//                data: JSON.stringify(jsondata),
//                contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/admin/get-category",
            success: function (data) {
//                    location.reload(true);
                console.log(data.categoryDtos);
                $(".get-category-parent-list").html("");
                var html = "";
                var categoryDtos = data.categoryDtos;
                for (var i = 0; i < categoryDtos.length; i++) {
                    var category = categoryDtos[i];
                    html = html + "<li class=\"parentcategory\" id=\"" + category.categoryId + "\"><a href=\"#\">" + category.categoryName + "</a></li>";
                }
                $(".get-category-parent-list").append(html);
                console.log(html);
            },
            complete: function () {
                return false;
            }
        });
    });
    //*********************Get Category spec ******************

/*    $(".get-category-spec").click(function () {
        $.ajax({
            type: "GET",
//                data: JSON.stringify(jsondata),
//                contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/admin/get-category",
            success: function (data) {
//                    location.reload(true);
                console.log(data.categoryDtos);
                $(".get-category-spec-list").html("");
                var html = "";
                var categoryDtos = data.categoryDtos;
                for (var i = 0; i < categoryDtos.length; i++) {
                    var category = categoryDtos[i];
                    html = html + "<li class=\"categoryspec\" id=\"" + category.categoryId + "\"><a href=\"#\">" + category.categoryName + "</a></li>";
                }
                $(".get-category-spec-list").append(html);
                console.log(html);
            },
            complete: function () {
                return false;
            }
        });
    });*/
    //*********************Get Category ******************

    $(".get-category").click(function () {
        $.ajax({
            type: "GET",
//                data: JSON.stringify(jsondata),
//                contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/admin/get-category",
            success: function (data) {
//                    location.reload(true);
                console.log(data.categoryDtos);
                $(".get-category-list").html("");
                var html = "";
                var categoryDtos = data.categoryDtos;
                for (var i = 0; i < categoryDtos.length; i++) {
                    var category = categoryDtos[i];
                    html = html + "<li class=\"category\" id=\"" + category.categoryId + "\"><a href=\"#\">" + category.categoryName + "</a></li>";
                }
                $(".get-category-list").append(html);
                console.log(html);
            },
            complete: function () {
                return false;
            }
        });
    });
    //*********************Save Category *****************
    $("#addCategorySubmit").click(function () {
        var jsondata = {};
        jsondata.categoryName = $('#category-name').val();
        jsondata.patentId = $('#parentcategoryId').val();
        console.log($('#category-name').val());
        console.log($('#parentcategoryId').val());
        if ($('#category-name').val() !== null && $('#parentcategoryId').val() !== null && $('#category-name').val() !== "" && $('#parentcategoryId').val() !== "") {
            $.ajax({
                type: "POST",
                data: JSON.stringify(jsondata),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                url: "/admin/save-category",
                success: function (data) {
                },
                complete: function () {
                }
            });
        }
    });

//*********************Get Vendor ******************

    $(".get-vendor").click(function () {
        $.ajax({
            type: "GET",
            dataType: "json",
            url: "/admin/get-vendor",
            success: function (data) {
                console.log(data.vendorDtos);
                $(".get-vendor-list").html("");
                var html = "";
                var vendorDtos = data.vendorDtos;
                for (var i = 0; i < vendorDtos.length; i++) {
                    var vendor = vendorDtos[i];
                    html = html + "<li class=\"vendor\" id=\"" + vendor.userId + "\"><a href=\"#\">" + vendor.contactName + "</a></li>";
                }
                $(".get-vendor-list").append(html);
                console.log(html);
            },
            complete: function () {
                return false;
            }
        });
    });

    // ****************** Add Vendor ************************
    $("#addVendorSubmit").click(function () {
        var jsondata = {};
        jsondata.contactName = $('#VendorID').val();
        jsondata.streetOne = $('#StreetID').val();
        jsondata.streetTwo = $('#Street2ID').val();
        jsondata.city = $('#CityID').val();
        jsondata.state = $('#StateID').val();
        jsondata.country = $('#CountryID').val();
        jsondata.zipCode = $('#ZipID').val();
        jsondata.phone = $('#PhoneID').val();
        $.ajax({
            type: "POST",
            data: JSON.stringify(jsondata),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/admin/save-category",
            success: function (data) {
            },
            complete: function () {
            }
        });
    });

    //*********************Save Specification *****************
    $("#addSpecificationSubmit").click(function () {
        var jsondata = {};
        jsondata.id = $('#categoryId').val();
        jsondata.name = $('#addSpecificationId').val();
        jsondata.value = $('#addSpecificationValue').val();
        if ($('#categoryId').val() !== null && $('#addSpecificationId').val() !== null && $('#addSpecificationValue').val() !== null && $('#categoryId').val() !== "" && $('#addSpecificationId').val() !== "" && $('#addSpecificationValue').val() !== "") {
            $.ajax({
                type: "POST",
                data: JSON.stringify(jsondata),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                url: "/admin/save-specification",
                success: function (data) {
                    getSpecifications($("#categoryId").val());
                },
                complete: function () {
                }
            });
        }
    });
    //**************** Save Specification value *********************
    $("#addValueSubmit").click(function () {
        var jsondata = {};
        var value = $('#value-text').val();
        jsondata.id = $('#specId').val();
        jsondata.value = $('#value-text').val();
        if (value !== '' && value !== null) {
            $.ajax({
                type: "POST",
                data: JSON.stringify(jsondata),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                url: "/admin/save-specification-value",
                success: function (data) {
                    getSpecifications($("#categoryId").val());
                },
                complete: function () {
                }
            });
        }
    });

});

//*********************Get Specifications ******************
function getSpecifications(categoryId) {
    console.log(categoryId);
    $.ajax({
        type: "GET",
        url: "/admin/get-specifications?categoryId=" + categoryId,
        success: function (data) {
//                    location.reload(true);
            console.log(data.specifications);
            $("#fitures-div").html("");
            var html = "";
            var options = "";
            var specifications = data.specifications;
            for (var i = 0; i < specifications.length; i++) {
                options = "";
                var fiture = specifications[i];
                for (var j = 0; j < fiture.values.length; j++) {
                    var thisValue = fiture.values[j];
                    options = options + "<option value=\"" + thisValue + "\">" + thisValue + "</option>";
                }
                html = html + "<div class=\"row fitures-row\">" +
                        "                        <div class=\"col-sm-3 text-right\">" +
                        "                            <input type=\"checkbox\">" +
                        "                        </div>" +
                        "                        <div class=\"col-sm-3\">" +
                        "                            <span>" + fiture.name + "</span>" +
                        "                        </div>" +
                        "                        <div class=\"col-sm-3\">" +
                        "                            <select name=\"cars\">" + options +
                        "                            </select>" +
                        "                        </div>" +
                        "                        <div class=\"col-sm-3\">" +
                        "                            <input type=\"buttne\" value=\"Add value\" class=\"spcvalueid\" id=\"" + fiture.id + "\" data-toggle=\"modal\" data-target=\"#specificvalueModal\">" +
                        "                        </div>" +
                        "                    </div>";
            }
            $("#fitures-div").append(html);
            $("#newSpecificationbtn").show();
            console.log(html);
        },
        complete: function () {
            return false;
        }
    });
}

function findSpecifications() {
    var allOption = "";
    $("#fitures-div .fitures-row").each(function () {
        if ($(this).find('input').prop("checked") === true) {
            allOption = allOption + $(this).find('span').text() + ": " + $(this).find('select option:selected').text() + ",";
        }
    });
    return allOption;
}

function addImageTable(imgUrl) {
    var itemCount = 0;
    var objs = [];
    var html = "";
    var obj = {
        "ROW_ID": itemCount,
        "IMG_Size": imgUrl
    };
    objs.push(obj);
    itemCount++;
    html = "<tr id='tr" + itemCount + "' class='subimg'><td>" + obj['IMG_Size'] + " </td> </tr>";
    $("#bill_table2").append(html);
    $("#imgid").val("");
    $("#fileId").val("");
}

$body = $("body");
$(document).on({
    ajaxStart: function () {
        $body.addClass("loading");
    },
    ajaxStop: function () {
        $body.removeClass("loading");
    }
});