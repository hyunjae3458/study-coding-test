"""각 Main.java를 독립적으로 컴파일하고 제공된 예제만 비교합니다."""

import argparse
from pathlib import Path
import subprocess
import tempfile


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("problem", choices=["1", "2", "all"], default="all", nargs="?")
    args = parser.parse_args()
    root = Path(__file__).resolve().parent
    problems = {"1": "01-bus", "2": "02-warehouse"}
    selected = problems.items() if args.problem == "all" else [(args.problem, problems[args.problem])]
    passed = total = 0
    for number, directory in selected:
        folder = root / directory
        print(f"문제 {number}: {directory}", flush=True)
        with tempfile.TemporaryDirectory(prefix="assignment-java-") as build:
            compiled = subprocess.run(
                ["javac", "-encoding", "UTF-8", "-d", build, str(folder / "Main.java")],
                capture_output=True, text=True,
            )
            if compiled.returncode:
                print(compiled.stderr)
                return 1
            cases = [(folder / "input.txt", folder / "output.txt")]
            cases.extend(
                (path, path.with_name(path.name.replace("input", "output", 1)))
                for path in sorted((folder / "examples").glob("input*.txt"))
            )
            for index, (input_path, output_path) in enumerate(cases, 1):
                total += 1
                source = input_path.read_text(encoding="utf-8")
                expected = output_path.read_text(encoding="utf-8").strip()
                print(f"테스트 케이스 {index}")
                print(f"- 입력값: {source.strip()}")
                print(f"- 기댓값: {expected}")
                try:
                    result = subprocess.run(
                        ["java", "-cp", build, "Main"], input=source,
                        capture_output=True, text=True, timeout=10,
                    )
                except subprocess.TimeoutExpired:
                    print("- 결과: 실행 제한 10초 초과 ❌")
                    continue
                actual = result.stdout.strip()
                ok = result.returncode == 0 and actual == expected
                passed += int(ok)
                print(f"- 실제값: {actual if actual else '(출력 없음)'}")
                if result.returncode:
                    print(result.stderr)
                print(f"- 결과: {'통과 ✅' if ok else '실패 ❌'}")
    print(f"제공된 예제 {total}개 중 {passed}개 통과")
    return 0 if passed == total else 1


if __name__ == "__main__":
    raise SystemExit(main())
