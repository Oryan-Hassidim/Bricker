# zip the content of the src directory
Compress-Archive -Path (Get-ChildItem -Path ".\src\") -DestinationPath "$env:USERPROFILE\Desktop\ex2.zip" -Force
