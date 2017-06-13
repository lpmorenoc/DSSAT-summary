@echo off
setLocal EnableDelayedExpansion

set limit=100001
set file=eval.json
set lineCounter=1
set filenameCounter=1
set uploaderFile=uploader.bat

set name=
set extension=
for %%a in (%file%) do (
    set "name=%%~na"
    set "extension=%%~xa"
)

for /f "tokens=*" %%a in (%file%) do (
    set splitFile=!name!-part!filenameCounter!!extension!
    if !lineCounter! gtr !limit! (
        set /a filenameCounter=!filenameCounter! + 1
        set lineCounter=1
        echo Created !splitFile!.
        echo curl -k -i --raw -o !splitFile!.log -X POST "http://localhost:9200/evaluation/_bulk?pretty" -H "Content-Type: application/json" -H "User-Agent: Fiddler" -H "Host: localhost:9200" --data-binary @!splitFile!>>!uploaderFile!
    )
     echo %%a>> !splitFile!
     set /a lineCounter=!lineCounter! + 1

)
