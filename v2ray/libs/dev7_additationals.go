package libv2ray

import (
	"bytes"
	"encoding/base64"
	"encoding/json"
	"fmt"
	"github.com/dev7dev/uri-to-json/pkgs/outbound"
	"github.com/dev7dev/uri-to-json/pkgs/parser"
	"github.com/dev7dev/uri-to-json/pkgs/utils"
	v2serial "github.com/xtls/xray-core/infra/conf/serial"
	"strings"
)

func TestConfig(ConfigureFileContent string) error {
	_, err := v2serial.LoadJSONConfig(strings.NewReader(ConfigureFileContent))
	return err
}

func getOutboundJSONIntended(oStr string) string {
	var out bytes.Buffer
	err := json.Indent(&out, []byte(oStr), "", "  ")
	if err != nil {
		return oStr
	}
	return string(out.Bytes())
}

func getVMESSUriAttrs(oStr string) string {
	data, err := base64.StdEncoding.DecodeString(oStr)
	if err != nil {
		return oStr
	}
	return string(data)
}

func IsXrayURI(config string) bool {
	scheme := utils.ParseScheme(config)
	switch scheme {
	case parser.SchemeVmess:
		return true
	case parser.SchemeVless:
		return true
	case parser.SchemeTrojan:
		return true
	case parser.SchemeSS:
		return true
	default:
		return false
	}
}

func GetXrayOutboundFromURI(rawURI string) string {
	scheme := utils.ParseScheme(rawURI)
	if scheme == "" {
		return ""
	}
	if scheme == parser.SchemeVmess {
		baseVMESSUri := getVMESSUriAttrs(strings.Replace(rawURI, parser.SchemeVmess, "", 1))
		rawURI = parser.SchemeVmess + baseVMESSUri
	}
	ob := outbound.GetOutbound(outbound.XrayCore, rawURI)
	if ob != nil {
		ob.Parse(rawURI)
		return getOutboundJSONIntended(ob.GetOutboundStr())
	} else {
		fmt.Println("this is not parsable uri or not supported by this library.")
	}
	return ""
}
