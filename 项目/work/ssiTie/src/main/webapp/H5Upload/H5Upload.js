var H5Upload;

if (H5Upload == undefined) {
    H5Upload = function (settings) {
        this.initH5Upload(settings);
    };
}

H5Upload.instances = {};
H5Upload.movieCount = 0;
H5Upload.version = "2.2.0 2009-03-25";

H5Upload.prototype.initH5Upload = function (settings) {
    try {
        this.customSettings = {};	// A container where developers can place their own settings associated with this instance.
        this.settings = settings;
        this.eventQueue = [];
        this.movieName = "H5Upload_" + (H5Upload.movieCount++);
        this.movieElement = null;


        // Setup global control tracking
        H5Upload.instances[this.movieName] = this;

        // Load the settings.  Load the Flash movie.
        this.initSettings();
        this.loadHtml();
    } catch (ex) {
        delete H5Upload.instances[this.movieName];
        throw ex;
    }
};

H5Upload.prototype.loadHtml = function () {
    var targetElement, tempParent;

    // Make sure an element with the ID we are going to use doesn't already exist
    if (document.getElementById(this.movieName) !== null) {
        throw "ID " + this.movieName + " is already in use. The Flash Object could not be added";
    }

    // Get the element where we will be placing the flash movie
    targetElement = document.getElementById(this.settings.button_placeholder_id) || this.settings.button_placeholder;

    if (targetElement == undefined) {
        throw "Could not find the placeholder element: " + this.settings.button_placeholder_id;
    }

    // Append the container and load the flash
    tempParent = document.createElement("div");
    tempParent.innerHTML = this.getH5HTML();	// Using innerHTML is non-standard but the only sensible way to dynamically add Flash in IE (and maybe other browsers)
    targetElement.parentNode.replaceChild(tempParent.firstChild, targetElement);

    this.initFileElement();
    // Fix IE Flash/Form bug
    if (window[this.movieName] == undefined && this.fileElement) {
        window[this.movieName] = this.fileElement;
    }
}

H5Upload.prototype.getH5HTML = function () {
    return [
        '<div style="width:' + this.settings.button_width + 'px;height:' + this.settings.button_height
            + 'px;overflow:hidden;position:relative;">',
        '<div style="width:' + this.settings.button_width + 'px;height:' + this.settings.button_height
            + 'px;line-height:' + this.settings.button_height + 'px;text-align:center;' +'">'+
        this.settings.button_text +
        '</div>' +
        '<input type="file" id="' + this.movieName + '" ' + (this.customSettings.single ? '' :' multiple')+' accept="' + this.settings.file_types.replace(/;/g,',').replace(/\*/g,'') + '" style="position:absolute;left:0;top:0;opacity:0;height:' + this.settings.button_height + 'px">',
        '</div>'
    ].join("");
}

H5Upload.prototype.initFileElement = function () {
    var self = this;

    if (this.fileElement == undefined) {
        this.fileElement = $('#' + this.movieName);
        this.fileElement.bind('change', function (e) { self.onSelectFile(e) });
    }

    if (this.fileElement === null) {
        throw "Could not find Upload element";
    }

    return this.fileElement;
}
H5Upload.prototype.onSelectFile = function (e) {
    var files = this.fileElement[0].files;

    if (files.length > 0) {
        this.uploadNext(files, 0);
    }
}

H5Upload.prototype.uploadNext = function (files, idx) {
    var self = this
    if (files.length > idx) {
        this.uploadFile(files[idx], function () {
            self.uploadNext(files, idx + 1);
        })
    } else {
        this.queueEvent('upload_complete_handler', [files]);
    }
}

H5Upload.prototype.uploadFile = function (file, uploadNext) {
    var self=this
    var options = {
        url: this.settings.upload_url,
        type: 'POST',
        dataType: 'text',
        success: function (json,xmlRequest) {
            if (xmlRequest.status == 200) {
                // self.queueEvent('upload_success_handler', [file, json]);
                uploadNext(true, json)
            } else {
                self.queueEvent('upload_error_handler', [file, 1, json.msg]);
                uploadNext(false, json)
            }
        },
        error: function (xhr) {
            self.queueEvent('upload_error_handler', [file]);
            uploadNext(false)

        }
    };
    options.data = new FormData();
    options.data.append('Filedata', file);
    options.cache = false;
    options.processData = false;
    options.contentType = false;
    options.xhr = function () { //用以显示上传进度
        var xhr = $.ajaxSettings.xhr();
        if (xhr.upload) {
            xhr.upload.addEventListener('progress', function (event) {
                console.log("+++++")
                console.log(event)
                var percent = Math.floor(event.loaded / event.total * 100);
                self.queueEvent('upload_progress_handler', [file, event.loaded]);
            }, false);
        }
        return xhr;
    };
    $.ajax(options);
}

// 屏蔽flash方法调用
H5Upload.prototype.callFlash = function (functionName, argumentArray) {
    console.log('call:' + functionName, argumentArray);
    return {};
}
// 将未重写的方法从SWFUpload拿过来
for (var funcname in SWFUpload.prototype) {
    if (!H5Upload.prototype[funcname]) {
        H5Upload.prototype[funcname] = SWFUpload.prototype[funcname];
    }
}