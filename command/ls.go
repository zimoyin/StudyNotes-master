package main

import (
	"flag"
	"fmt"
	"io/ioutil"
	"os"
	"os/user"
	"path/filepath"
	"sort"
	"strconv"
	"syscall"
	"strings"
	"unsafe"
)

var (
	rn         = flag.Bool("rn", false, "Ls will try to output results on one line as much as possible")
	all        = flag.Bool("a", false, "display all files including hidden files")
	allExcept  = flag.Bool("A", false, "display all files excluding '.' and '..'")
	longFormat = flag.Bool("l", false, "use a long listing format")
	reverse    = flag.Bool("r", false, "reverse order while sorting")
	recursive  = flag.Bool("R", false, "list subdirectories recursively")
	sortSize   = flag.Bool("S", false, "sort by file size")
	sortTime   = flag.Bool("t", false, "sort by modification time")
	dirOnly    = flag.Bool("d", false, "list directories themselves, not their contents")
	noSort     = flag.Bool("f", false, "do not sort")
	appendInfo = flag.Bool("F", false, "append indicator (one of */=>@|) to entries")
	noOwner    = flag.Bool("g", false, "like -l, but do not list owner")
	noGroup    = flag.Bool("G", false, "in a long listing, don't print group names")
	showAuthor = flag.Bool("author", false, "[Not implemented] print the author of each file")
	numID      = flag.Bool("n", false, "like -l, but list numeric user and group IDs")
	humanSize  = flag.Bool("h", false, "with -l, print sizes in human readable format (e.g., 1K 234M 2G)")
	ctime      = flag.Bool("c", false, "with -l, print ctime and sort by ctime")
	byColumn   = flag.Bool("C", false, "[Not implemented] list entries by columns")
	noColor    = flag.Bool("noColor", false, "never show color")
	fullTime   = flag.Bool("time", false, "like -l -time")
	depth      = 0
)

func main() {
	// Parse normal flags
	flag.Parse()

	paths := flag.Args()
	if len(paths) == 0 {
		paths = []string{"."}
	}

	for _, path := range paths {
		listFiles(path)
	}
}

func listFiles(path string) {
	var files []os.FileInfo
	var err error

	if *dirOnly {
		info, err := os.Stat(path)
		if err != nil {
			fmt.Println("Error:", err)
			return
		}
		files = []os.FileInfo{info}
	} else {
		files, err = ioutil.ReadDir(path)
		if err != nil {
			fmt.Println("Error:", err)
			return
		}
	}

	// Filter out hidden files unless -a or -A is set
	if !*all && !*allExcept {
		var filteredFiles []os.FileInfo
		for _, file := range files {
			if !isHidden(file) {
				filteredFiles = append(filteredFiles, file)
			}
		}
		files = filteredFiles
	}

	if *allExcept {
		var filteredFiles []os.FileInfo
		for _, file := range files {
			if file.Name() != "." && file.Name() != ".." {
				filteredFiles = append(filteredFiles, file)
			}
		}
		files = filteredFiles
	}

	if *noSort {
		// no sorting needed, So don't implement it here
	} else if *sortSize {
		sort.Slice(files, func(i, j int) bool {
			if *reverse {
				return files[i].Size() > files[j].Size()
			}
			return files[i].Size() < files[j].Size()
		})
	} else if *sortTime || *ctime {
		sort.Slice(files, func(i, j int) bool {
			if *reverse {
				return files[i].ModTime().Before(files[j].ModTime())
			}
			return files[i].ModTime().After(files[j].ModTime())
		})
	} else {
		sort.Slice(files, func(i, j int) bool {
			if *reverse {
				return files[i].Name() > files[j].Name()
			}
			return files[i].Name() < files[j].Name()
		})
	}
	
	var line = ""
	var width = getConsoleScreenWidth()

	// 输出文件
	for _, file := range files {
		if *longFormat {
			printLongFormat(path, file)
		} else {
			if !*rn {
				fmt.Println(space() + setStringColor(file))
			} else {
				var st = line + fmt.Sprintf("%s%s",setStringColor(file),sp(25-len(file.Name())))
				if float64(len(st)) >= (width*1.1) {
					fmt.Println(line)
					line = ""
					line +=  fmt.Sprintf("%s%s",setStringColor(file),sp(25-len(file.Name())))
				}else{
					line +=  fmt.Sprintf("%s%s",setStringColor(file),sp(25-len(file.Name())))
				}
			}
		}
		if *recursive {
			if file.IsDir() {
				depth++
				listFiles(filepath.Join(path, file.Name()))
				depth--
			}
		}
	}
	fmt.Println(line)
}

func sp(i int) string{
	var s = ""
	if i <=0 {
		return "  "
	}
	for _ = range i {
		s += " "
	}
	return s
}

func space() string {
	if !*recursive {
		return ""
	}
	var sp = "|"
	for _ = range depth {
		sp += " | "
	}
	return sp
}

func printLongFormat(path string, file os.FileInfo) {
	modTime := file.ModTime()
	var owner, group string

	if *numID || *noOwner {
		owner = strconv.Itoa(os.Getuid())
	} else {
		usr, err := user.LookupId(strconv.Itoa(os.Getuid()))
		if err != nil {
			// owner = strconv.Itoa(os.Getuid())
			owner = "-"
		} else {
			owner = usr.Username
		}
	}

	if *numID || *noGroup {
		group = strconv.Itoa(os.Getgid())
	} else {
		grp, err := user.LookupGroupId(strconv.Itoa(os.Getgid()))
		if err != nil {
			// group = strconv.Itoa(os.Getgid())
			group = "-"
		} else {
			group = grp.Name
		}
	}

	if *fullTime {
		fmt.Printf("%s%s %-4d %-3s %-4s %-11s %13s  %s\n",
			space(),
			file.Mode().String(),
			getLinkCount(path, file),
			owner,
			group,
			formatSize(file.Size()),
			modTime.Format("2006-01-02 15:04:05.000000000"),
			setStringColor(file))
	} else {
		fmt.Printf("%s%s %-4d %-3s %-4s %-11s %13s  %s\n",
			space(),
			file.Mode().String(),
			getLinkCount(path, file),
			owner,
			group,
			formatSize(file.Size()),
			modTime.Format("Jan 02 2006"),
			setStringColor(file))
	}
}

func setStringColor(file os.FileInfo) string {
	str := file.Name()
	if *noColor {
		return str
	}
	// 可执行文件后缀名数组
	exts := []string{".exe", ".bat", ".cmd", ".com", ".sh",".app"}
	hidden := isHidden(file)
	// 文件夹：蓝色
	if file.IsDir() {
		// 隐藏文件: 斜体
		if hidden {
			return "\033[3;34m"+str+"\033[0m"
		}
		return "\033[34m"+str+"\033[0m"
	}

	// 获取后缀名
	is := false
	ext := filepath.Ext(file.Name())
	for _, e := range exts {
		if ext == e {
			is = true
		}
	}
	// 可执行文件: 绿色
	if is {
		// 隐藏文件: 斜体
		if hidden {
			return "\033[3;32m"+str+"\033[0m"
		}
		return "\033[32m"+str+"\033[0m"
	}

	// 隐藏文件: 斜体
	if hidden {
		return "\033[3m"+str+"\033[0m"
	}

	return "\033[0m"+str
}

func getLinkCount(path string, file os.FileInfo) int {
	fullPath := filepath.Join(path, file.Name())
	if file.IsDir() {
		entries, err := ioutil.ReadDir(fullPath)
		if err != nil {
			return 1
		}
		return len(entries)
	}
	return 1
}

func formatSize(size int64) string {
	if !*humanSize {
		return strconv.FormatInt(size, 10)
	}
	const unit = 1024 // B
	if size < unit {
		return fmt.Sprintf("%-4d %-1s", size,"B")
	}
	div, exp := int64(unit), 0
	for n := size / unit; n >= unit; n /= unit {
		div *= unit
		exp++
	}
	unitStr := strings.TrimSpace(fmt.Sprintf("%c","KMGTPE"[exp]))
	return fmt.Sprintf("%-4.1f %-2s", float64(size)/float64(div), unitStr+"B")
}

func isHidden(file os.FileInfo) bool {
	if file.Name()[0] == '.' {
		return true
	}

	// Check for hidden file attribute on Windows
	if stat, ok := file.Sys().(*syscall.Win32FileAttributeData); ok {
		return stat.FileAttributes&syscall.FILE_ATTRIBUTE_HIDDEN != 0
	}

	return false
}


/// 窗口大小
type (
    short int16
    word  uint16
    dword uint32
    coord struct {
        X short
        Y short
    }
    smallRect struct {
        Left   short
        Top    short
        Right  short
        Bottom short
    }
    consoleScreenBufferInfo struct {
        Size              coord
        CursorPosition    coord
        Attributes        word
        Window            smallRect
        MaximumWindowSize coord
    }
)

var kernel32 = syscall.NewLazyDLL("kernel32.dll")
var procGetConsoleScreenBufferInfo = kernel32.NewProc("GetConsoleScreenBufferInfo")

func getConsoleScreenBufferInfo(handle syscall.Handle) (consoleScreenBufferInfo, error) {
    var csbi consoleScreenBufferInfo
    ret, _, err := procGetConsoleScreenBufferInfo.Call(uintptr(handle), uintptr(unsafe.Pointer(&csbi)))
    if ret == 0 {
        return csbi, err
    }
    return csbi, nil
}

func getConsoleScreenWidth() float64 {
	handle := syscall.Handle(os.Stdout.Fd())
    csbi, err := getConsoleScreenBufferInfo(handle)
    if err != nil {
        fmt.Fprintln(os.Stderr, "Error getting console screen buffer info:", err)
		return float64(-1)
    }
	return float64(csbi.Size.X)
}